package com.suems.mappers;

import com.suems.dto.EnergySourceDto;
import com.suems.dto.SensorDataDto;
import com.suems.model.EnergySource;
import com.suems.model.EnergyStatus;
import com.suems.model.SensorData;

public class Mappers {

    private Mappers(){}

    public static SensorDataDto toDto(SensorData e)
    {
        SensorDataDto d = new SensorDataDto();
        d.id=e.getId();
        d.timestamp=e.getTimestamp();
        d.solarPower=e.getSolarPower();
        d.windPower=e.getWindPower();
        d.gridUsage=e.getGridUsage();
        d.totalConsumption=e.getTotalConsumption();

        return d;
    }

    public static EnergySourceDto toDto(EnergySource e)
    {
        EnergySourceDto d = new EnergySourceDto();
        d.id= e.getId();
        d.type=e.getType();
        d.name=e.getName();
        d.capacityKw=e.getCapacityKw();
        d.status=e.getStatus();
        d.location=e.getLocation();

        return d;
    }

    public static EnergySource toEntity(EnergySourceDto d){
        EnergySource e = new EnergySource();
        e.setType(d.type);
        e.setName(d.name);
        e.setCapacityKw(d.capacityKw);
        e.setStatus(d.status != null ? d.status : EnergyStatus.ACTIVE);
        e.setLocation(d.location);
        return e;
    }

    public static void copyToEntity(EnergySourceDto d, EnergySource e){
        if(d.type != null) e.setType(d.type);
        if(d.name != null) e.setName(d.name);
        if(d.capacityKw >= 0) e.setCapacityKw(d.capacityKw);
        if(d.status != null) e.setStatus(d.status);
        if(d.location != null) e.setLocation(d.location);
    }
    
}
