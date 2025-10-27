package com.suems.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class EnergySource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EnergyType type;

    @NotBlank
    private String name;

    @PositiveOrZero
    private double capacityKw;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EnergyStatus status = EnergyStatus.ACTIVE;

    private String location;

    public EnergySource(){}

    public EnergySource(Long id, EnergyType type, String name, double capacityKw, EnergyStatus status, String location) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.capacityKw = capacityKw;
        this.status = status;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull EnergyType getType() {
        return type;
    }

    public void setType(@NotNull EnergyType type) {
        this.type = type;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    @PositiveOrZero
    public double getCapacityKw() {
        return capacityKw;
    }

    public void setCapacityKw(@PositiveOrZero double capacityKw) {
        this.capacityKw = capacityKw;
    }

    public @NotNull EnergyStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull EnergyStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
