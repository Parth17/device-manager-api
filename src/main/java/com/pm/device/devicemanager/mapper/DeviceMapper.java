package com.pm.device.devicemanager.mapper;

import org.springframework.stereotype.Component;

import com.pm.device.devicemanager.domain.Device;
import com.pm.device.devicemanager.dto.DeviceDto;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceDto dto) {
        Device device = new Device();
        device.setName(dto.getName());
        device.setBrand(dto.getBrand());
        device.setState(dto.getState());
        // Creation time is set automatically in entity
        return device;
    }

    public DeviceDto toDto(Device device) {
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setBrand(device.getBrand());
        dto.setState(device.getState());
        dto.setCreationTime(device.getCreationTime());
        return dto;
    }

    public void updateEntityFromDto(DeviceDto dto, Device device) {
        if (dto.getName() != null) {
            device.setName(dto.getName());
        }
        if (dto.getBrand() != null) {
            device.setBrand(dto.getBrand());
        }
        if (dto.getState() != null) {
            device.setState(dto.getState());
        }
        // Creation time not updated
    }
}

