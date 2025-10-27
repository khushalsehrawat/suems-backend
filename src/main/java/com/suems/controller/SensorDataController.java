package com.suems.controller;

import com.suems.dto.SensorDataDto;
import com.suems.dto.SummaryDto; // if you have it; or map from service.Summary
import com.suems.mappers.Mappers;
import com.suems.model.SensorData;
import com.suems.model.User;
import com.suems.repository.UserRepository;
import com.suems.service.SensorDataService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorDataController {

    private final SensorDataService service;

    private final UserRepository userRepository;

    public SensorDataController(SensorDataService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    /*@GetMapping("/recent")
    public List<SensorDataDto> recentTop100(@AuthenticationPrincipal User user){
        return service.findRecentTop100(user.getId()).stream().map(Mappers::toDto).toList();
    }*/



    /*@GetMapping("/recent")
    public List<SensorData> recent(@AuthenticationPrincipal User user) {
        return service.findRecentTop100(user);

    }*/

    /*@GetMapping("/recent")
    public List<SensorDataDto> recent(@AuthenticationPrincipal User user) {
        return service.findRecentTop100(user)
                .stream()
                .map(Mappers::toDto)
                .toList();
    }
*/


    @GetMapping("/recent")
    public ResponseEntity<?> recent(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized - please log in"));
        }

        if (!userRepository.existsById(user.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found in DB"));
        }

        List<SensorData> data = service.findRecentTop100(user);
        return ResponseEntity.ok(data);
    }




    @GetMapping("/after")
    public List<SensorDataDto> findAfter(@AuthenticationPrincipal User user,
                                         @RequestParam("ts") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime ts) {
        return service.findAfter(user, ts).stream().map(Mappers::toDto).toList();
    }




    @PostMapping("/saveWithCost")
    public SensorData saveWithCost(
            @AuthenticationPrincipal User user,
            @RequestBody SensorData data
    ) {
        return service.saveWithCost(data, user);
    }


    @PostMapping("/manual-save")
    public SensorData manualSave(@RequestParam Long userId, @RequestBody SensorData data) {
        User u = new User();
        u.setId(userId);
        return service.saveWithCost(data, u);
    }




    @GetMapping("/summary")
    public SummaryDto summaryDto(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "24") int hours) {
        if (hours <= 0) hours = 24;
        // If you have your own summarizeLastHour(int) method, adapt it to per-user. Otherwise,
        // map from SensorDataService.calculateSummary(userId, "day", ...)
        var s = service.calculateSummary(user, "day", null, null);
        SummaryDto dto = new SummaryDto();
        dto.setSamples(s.getSamples());
        dto.setAvgSolar(s.getAvgSolar());
        dto.setAvgWind(s.getAvgWind());
        dto.setAvgGrid(s.getAvgGrid());
        dto.setAvgConsumption(s.getAvgConsumption());
        dto.setTotalSolar(s.getTotalSolar());
        dto.setTotalWind(s.getTotalWind());
        dto.setTotalGrid(s.getTotalGrid());
        dto.setTotalConsumption(s.getTotalConsumption());
        // add any additional fields you have
        return dto;
    }
}