package com.pm.device.devicemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pm.device.devicemanager.domain.Device;
import com.pm.device.devicemanager.domain.DeviceState;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
    List<Device> findByState(DeviceState state);
}
