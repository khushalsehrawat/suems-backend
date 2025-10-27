package com.suems.controller;

import com.suems.config.SimulatorConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ✅ REST controller that allows admin or user to dynamically
 * modify simulator settings at runtime.
 */
@RestController
@RequestMapping("/api/simulator")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class SimulatorController {

    private final SimulatorConfig simulatorConfig;

    public SimulatorController(SimulatorConfig simulatorConfig) {
        this.simulatorConfig = simulatorConfig;
    }

    /**
     * 🔹 Update simulator interval in milliseconds.
     * Example: PUT /api/simulator/interval?ms=3600000  (1 hour)
     */
    @PutMapping("/interval")
    public ResponseEntity<String> updateInterval(@RequestParam long ms) {
        simulatorConfig.setIntervalMs(ms);
        return ResponseEntity.ok("✅ Simulation interval updated to " + ms + " ms (" + ms / 1000 + " seconds)");
    }

    /**
     * 🔹 Enable or disable simulator
     * Example: PUT /api/simulator/enable?value=false
     */
    @PutMapping("/enable")
    public ResponseEntity<String> toggleSimulator(@RequestParam boolean value) {
        simulatorConfig.setEnabled(value);
        return ResponseEntity.ok("✅ Simulator is now " + (value ? "ENABLED" : "DISABLED"));
    }

    /**
     * 🔹 View current simulator status
     * Example: GET /api/simulator/status
     */
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        String who = simulatorConfig.getTargetUserId() == null ? "(unassigned)" : String.valueOf(simulatorConfig.getTargetUserId());
        return ResponseEntity.ok(
                "Simulator is " + (simulatorConfig.isEnabled() ? "enabled" : "disabled") +
                        " | Interval: " + simulatorConfig.getIntervalMs() + " ms" +
                        " | Target user: " + who
        );
    }

    /**
     * Assign a target user id for the simulator. Future readings will be saved for this user.
     */
    @PutMapping("/target-user")
    public ResponseEntity<String> assignTargetUser(@RequestParam Long userId) {
        simulatorConfig.setTargetUserId(userId);
        return ResponseEntity.ok("Simulator target user set to " + userId);
    }

    @GetMapping("/interval")
    public String getInterval() {
        return "Current simulator interval: " + simulatorConfig.getIntervalMs() + " ms";
    }
}
