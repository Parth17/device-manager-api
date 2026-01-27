package com.example.devicemanager.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pm.device.devicemanager.domain.Device;
import com.pm.device.devicemanager.domain.DeviceState;
import com.pm.device.devicemanager.dto.DeviceDto;
import com.pm.device.devicemanager.mapper.DeviceMapper;
import com.pm.device.devicemanager.repository.DeviceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DeviceService {

    private final DeviceRepository repository;
    private final DeviceMapper mapper;

    public DeviceService(DeviceRepository repository, DeviceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DeviceDto create(DeviceDto dto) {
        Device device = mapper.toEntity(dto);
        Device saved = repository.save(device);
        return mapper.toDto(saved);
    }

    public DeviceDto update(Long id, DeviceDto dto, boolean partial) {
        Device device = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));

        if (device.getState() == DeviceState.IN_USE) {
            if (dto.getName() != null || dto.getBrand() != null) {
                throw new IllegalStateException("Cannot update name or brand for in-use device");
            }
        }

        if (partial) {
            mapper.updateEntityFromDto(dto, device);
        } else {
            // Full update, but still protect name/brand if in-use (already checked)
            device.setName(dto.getName());
            device.setBrand(dto.getBrand());
            device.setState(dto.getState());
        }

        Device updated = repository.save(device);
        return mapper.toDto(updated);
    }

    public DeviceDto getById(Long id) {
        Device device = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        return mapper.toDto(device);
    }

    public List<DeviceDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<DeviceDto> getByBrand(String brand) {
        return repository.findByBrand(brand).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<DeviceDto> getByState(DeviceState state) {
        return repository.findByState(state).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Device device = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        if (device.getState() == DeviceState.IN_USE) {
            throw new IllegalStateException("Cannot delete in-use device");
        }
        repository.delete(device);
    }
}

