package com.suems.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

/**
 * Per-user configuration:
 * - unitCost (currency/kWh)
 * - lastUpdated
 */

@Entity
public class UserEnergyConfig {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private double unitCost; // e.g., INR/kWh or USD/kWh

    private LocalDateTime lastUpdated = LocalDateTime.now();

    public UserEnergyConfig(){}

    public UserEnergyConfig(Long id, Long userId, double unitCost, LocalDateTime lastUpdated) {
        this.id = id;
        this.userId = userId;
        this.unitCost = unitCost;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
