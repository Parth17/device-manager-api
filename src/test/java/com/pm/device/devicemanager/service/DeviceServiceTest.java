package com.pm.device.devicemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.devicemanager.service.DeviceService;
import com.pm.device.devicemanager.domain.Device;
import com.pm.device.devicemanager.domain.DeviceState;
import com.pm.device.devicemanager.dto.DeviceDto;
import com.pm.device.devicemanager.mapper.DeviceMapper;
import com.pm.device.devicemanager.repository.DeviceRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository repository;

    @Mock
    private DeviceMapper mapper;

    @InjectMocks
    private DeviceService service;

    private Device device;
    private DeviceDto dto;

    @BeforeEach
    void setUp() {
        device = new Device();
        device.setId(1L);
        device.setName("Test Device");
        device.setBrand("Test Brand");
        device.setState(DeviceState.AVAILABLE);

        dto = new DeviceDto();
        dto.setName("Updated Name");
        dto.setBrand("Updated Brand");
        dto.setState(DeviceState.INACTIVE);
    }

    @Test
    void create() {
        when(mapper.toEntity(any(DeviceDto.class))).thenReturn(device);
        when(repository.save(any(Device.class))).thenReturn(device);
        when(mapper.toDto(any(Device.class))).thenReturn(dto);

        DeviceDto result = service.create(dto);
        assertNotNull(result);
        verify(repository).save(any(Device.class));
    }

    @Test
    void updatePartialAvailable() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        when(mapper.toDto(any(Device.class))).thenReturn(dto);

        DeviceDto result = service.update(1L, dto, true);
        assertNotNull(result);
        verify(repository).save(device);
    }

    @Test
    void updateInUseNameThrowsException() {
        device.setState(DeviceState.IN_USE);
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        assertThrows(IllegalStateException.class, () -> service.update(1L, dto, true));
    }

    @Test
    void getById() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));
        when(mapper.toDto(device)).thenReturn(dto);

        DeviceDto result = service.getById(1L);
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void getByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void deleteAvailable() {
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        service.delete(1L);
        verify(repository).delete(device);
    }

    @Test
    void deleteInUseThrowsException() {
        device.setState(DeviceState.IN_USE);
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        assertThrows(IllegalStateException.class, () -> service.delete(1L));
    }
}

