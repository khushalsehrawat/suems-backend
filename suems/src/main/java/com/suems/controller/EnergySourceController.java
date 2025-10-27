package com.suems.controller;


import com.suems.dto.EnergySourceDto;
import com.suems.mappers.Mappers;
import com.suems.model.EnergySource;
import com.suems.service.EnergySourceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sources")
public class EnergySourceController {

    private final EnergySourceService service;

    public EnergySourceController(EnergySourceService service) {
        this.service = service;
    }

    @GetMapping
    public List<EnergySourceDto> all()
    {
        return service.findAll().stream().map(Mappers::toDto).toList();
    }

    @PostMapping
    public EnergySourceDto create(@RequestBody @Valid EnergySourceDto dto)
    {
        EnergySource saved = service.create(Mappers.toEntity(dto));
        return Mappers.toDto(saved);
    }

    @PostMapping("/{id}")
    public EnergySourceDto get(@PathVariable Long id)
    {
        return Mappers.toDto(service.get(id));
    }

    @PutMapping("/{id}")
    public EnergySourceDto update(@PathVariable Long id, @RequestBody @Valid EnergySourceDto dto) {
        EnergySource entity = service.get(id);
        Mappers.copyToEntity(dto, entity);
        return Mappers.toDto(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}