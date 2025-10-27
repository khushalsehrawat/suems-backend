package com.suems.simulator;

import com.suems.model.SensorData;
import com.suems.model.User;
import com.suems.repository.UserRepository;
import com.suems.service.SensorDataService;
import com.suems.config.SimulatorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataSimulator {

    private final SensorDataService sensorDataService;
    private final SimulatorConfig simulatorConfig;
    private final UserRepository userRepository;
    private final Random random = new Random();

    @Value("${suems.simulator.enabled:true}")
    private boolean enabled;

    // Default: 10 seconds (can be changed dynamically)
    private long intervalMs = 10000;

    @Autowired
    public DataSimulator(SensorDataService sensorDataService,
                         SimulatorConfig simulatorConfig,
                         UserRepository userRepository) {
        this.sensorDataService = sensorDataService;
        this.simulatorConfig = simulatorConfig;
        this.userRepository = userRepository;
    }

    // ✅ Runs every 10 seconds
    @Scheduled(fixedRateString = "10000")
    public void tick() {
        if (!enabled) return;

        SensorData s = new SensorData();
        double solar = clamp(0, 12, 2 + random.nextDouble() * 10);
        double wind  = clamp(0, 8,  1 + random.nextDouble() * 6);
        double grid  = clamp(0, 10, 1 + random.nextDouble() * 8);

        double consumption = clamp(0, 20, solar + wind + grid - random.nextDouble() * 3);
        s.setSolarPower(solar);
        s.setWindPower(wind);
        s.setGridUsage(grid);
        s.setTotalConsumption(consumption);

        Long uid = simulatorConfig.getTargetUserId();
        if (uid != null) {
            User user = userRepository.findById(uid)
                    .orElseThrow(() -> new RuntimeException("Simulator target user not found with ID: " + uid));
            s.setUserId(user);
            sensorDataService.saveWithCost(s, user);
        } else {
            // default: assign to any test user if none specified
            User fallback = userRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No users found for simulator fallback"));
            s.setUserId(fallback);
            sensorDataService.saveWithCost(s, fallback);
        }

    }

    private double clamp(double min, double max, double val) {
        return Math.max(min, Math.min(max, val));
    }

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }

    public long getIntervalMs() {
        return intervalMs;
    }
}
