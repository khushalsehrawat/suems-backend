package com.suems.service;


import com.suems.exception.NotFoundException;
import com.suems.model.EnergySource;
import com.suems.repository.EnergySourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergySourceService {

    private final EnergySourceRepository energySourceRepository;

    public EnergySourceService(EnergySourceRepository energySourceRepository) {
        this.energySourceRepository = energySourceRepository;
    }

    public List<EnergySource> findAll(){
        return energySourceRepository.findAll();
    }

    public EnergySource create(EnergySource e){
        return energySourceRepository.save(e);
    }

    public EnergySource get(Long id){
        return energySourceRepository.findById(id).orElseThrow(()->
                new NotFoundException("Energy Source id = " + id + " not found!"));
    }

    public EnergySource update(Long id, EnergySource updated)
    {
        EnergySource existing = get(id);
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setCapacityKw(updated.getCapacityKw());
        existing.setStatus(updated.getStatus());
        existing.setLocation(updated.getLocation());

        return energySourceRepository.save(existing);
    }

    public void delete(Long id){
        energySourceRepository.delete(get(id));
    }

}
