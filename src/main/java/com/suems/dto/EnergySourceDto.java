package com.suems.dto;

import com.suems.model.EnergyStatus;
import com.suems.model.EnergyType;
public class EnergySourceDto {

    public Long id;
    public EnergyType type;
    public String name;
    public double capacityKw;
    public EnergyStatus status = EnergyStatus.ACTIVE;
    public String location;

}
