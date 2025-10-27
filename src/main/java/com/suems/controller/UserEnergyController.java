package com.suems.controller;

import com.suems.model.User;
import com.suems.model.UserEnergyConfig;
import com.suems.service.SensorDataService;
import com.suems.service.UserEnergyConfigService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Endpoints for:
 * - set/get unit cost
 * - reset all config
 * - delete hour data
 * - averages (day/week/month/custom)
 * - export CSV between two timestamps
 */
@RestController
@RequestMapping("/api/user-energy")
public class UserEnergyController {

    private final UserEnergyConfigService cfg;
    private final SensorDataService data;

    public UserEnergyController(UserEnergyConfigService cfg, SensorDataService data) {
        this.cfg = cfg; this.data = data;
    }

    @PostMapping("/set-cost")
    public ResponseEntity<UserEnergyConfig> setCost(
            @AuthenticationPrincipal User user,
            @RequestParam @Min(0) double unitCost) {
        return ResponseEntity.ok(cfg.saveOrUpdate(user.getId(), unitCost));
    }

    @GetMapping("/get-cost")
    public double getCost(@AuthenticationPrincipal User user) {
        return cfg.getUnitCostOrDefault(user.getId());
    }

    @DeleteMapping("/reset")
    public void reset(@AuthenticationPrincipal User user) {
        cfg.reset(user.getId());
    }

    @DeleteMapping("/delete-hour")
    public long deleteHour(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hour) {
        return data.deleteHour(user, hour);
    }

    @GetMapping("/average")
    public SensorDataService.Summary average(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "day") String range,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return data.calculateSummary(user, range, from, to);
    }

    @GetMapping("/export")
    public void exportCsv(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            HttpServletResponse response) throws IOException {

        String csv = data.exportCsv(user, from, to);
        String filename = "suems-data-" + from.toLocalDate() + "_to_" + to.toLocalDate() + ".csv";

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        response.getWriter().write(csv);
    }
}
