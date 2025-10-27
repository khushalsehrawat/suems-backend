package com.suems.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ✅ A central config holder for simulator settings.
 * This lets us change the simulation interval dynamically via API.
 */
@Component
public class SimulatorConfig {

    @Value("${suems.simulator.enabled:true}")
    private boolean enabled;

    @Value("${suems.simulator.interval-ms:10000}")
    private long intervalMs;

    @Value("${suems.simulator.target-user-id:1}")
    private Long targetUserId; // ✅ default to user id 1

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getIntervalMs() {
        return intervalMs;
    }

    public void setIntervalMs(long intervalMs) {
        this.intervalMs = intervalMs;
    }

    public Long getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Long targetUserId) { this.targetUserId = targetUserId; }
}
