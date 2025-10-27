package com.suems.dto;

public class SummaryDto {

    public Long samples;
    public double avgSolar;
    public double avgWind;
    public double avgGrid;
    public double avgConsumption;
    public double totalSolar;
    public double totalWind;
    public double totalGrid;
    public double totalConsumption;

    public SummaryDto(){}

    public SummaryDto(Long samples, double avgSolar, double avgWind, double avgGrid, double avgConsumption, double totalSolar, double totalWind, double totalGrid, double totalConsumption) {
        this.samples = samples;
        this.avgSolar = avgSolar;
        this.avgWind = avgWind;
        this.avgGrid = avgGrid;
        this.avgConsumption = avgConsumption;
        this.totalSolar = totalSolar;
        this.totalWind = totalWind;
        this.totalGrid = totalGrid;
        this.totalConsumption = totalConsumption;
    }

    public Long getSamples() {
        return samples;
    }

    public void setSamples(Long samples) {
        this.samples = samples;
    }

    public double getAvgSolar() {
        return avgSolar;
    }

    public void setAvgSolar(double avgSolar) {
        this.avgSolar = avgSolar;
    }

    public double getAvgWind() {
        return avgWind;
    }

    public void setAvgWind(double avgWind) {
        this.avgWind = avgWind;
    }

    public double getAvgGrid() {
        return avgGrid;
    }

    public void setAvgGrid(double avgGrid) {
        this.avgGrid = avgGrid;
    }

    public double getAvgConsumption() {
        return avgConsumption;
    }

    public void setAvgConsumption(double avgConsumption) {
        this.avgConsumption = avgConsumption;
    }

    public double getTotalSolar() {
        return totalSolar;
    }

    public void setTotalSolar(double totalSolar) {
        this.totalSolar = totalSolar;
    }

    public double getTotalWind() {
        return totalWind;
    }

    public void setTotalWind(double totalWind) {
        this.totalWind = totalWind;
    }

    public double getTotalGrid() {
        return totalGrid;
    }

    public void setTotalGrid(double totalGrid) {
        this.totalGrid = totalGrid;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
