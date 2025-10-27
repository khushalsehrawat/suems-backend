package com.suems.service;


import com.suems.model.User;
import com.suems.model.UserEnergyConfig;
import com.suems.repository.UserEnergyConfigRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserEnergyConfigService {

    private final UserEnergyConfigRepository repository;

    public UserEnergyConfigService(UserEnergyConfigRepository repository) {
        this.repository = repository;
    }

    public UserEnergyConfig saveOrUpdate(Long userId, double unitCost)
    {
        UserEnergyConfig cfg = repository.findByUserId(userId).orElseGet(UserEnergyConfig::new);
        cfg.setUserId(userId);
        cfg.setUnitCost(unitCost);
        cfg.setLastUpdated(LocalDateTime.now());
        return repository.save(cfg);
    }

    public double getUnitCostOrDefault(Long userId)
    {
        return repository.findByUserId(userId).map(UserEnergyConfig::getUnitCost).orElse(0.0);
    }

    public void reset(Long userId){
        repository.deleteByUserId(userId);
    }

}
