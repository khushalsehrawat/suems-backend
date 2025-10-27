package com.suems.dto;

import java.time.LocalDateTime;

public class SensorDataDto {

    public Long id;
    public LocalDateTime timestamp = LocalDateTime.now();
    public double solarPower;
    public double windPower;
    public double gridUsage;
    public double totalConsumption;


}
