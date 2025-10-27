package com.suems.repository;

import com.suems.model.EnergySource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergySourceRepository extends JpaRepository<EnergySource, Long> {
    
}
