package com.suems.repository;

import com.suems.model.UserEnergyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEnergyConfigRepository extends JpaRepository<UserEnergyConfig, Long> {

    Optional<UserEnergyConfig> findByUserId(Long userId);
    void deleteByUserId(Long userId);

}

