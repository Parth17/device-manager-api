package com.example.devicemanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devicemanager.service.DeviceService;
import com.pm.device.devicemanager.domain.DeviceState;
import com.pm.device.devicemanager.dto.DeviceDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Management", description = "API for managing devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new device")
    public ResponseEntity<DeviceDto> create(@Valid @RequestBody DeviceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Fully update an existing device")
    public ResponseEntity<DeviceDto> fullUpdate(@PathVariable Long id, @Valid @RequestBody DeviceDto dto) {
        return ResponseEntity.ok(service.update(id, dto, false));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing device")
    public ResponseEntity<DeviceDto> partialUpdate(@PathVariable Long id, @RequestBody DeviceDto dto) {
        return ResponseEntity.ok(service.update(id, dto, true));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch a single device by ID")
    public ResponseEntity<DeviceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "Fetch all devices")
    public ResponseEntity<List<DeviceDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Fetch devices by brand")
    public ResponseEntity<List<DeviceDto>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(service.getByBrand(brand));
    }

    @GetMapping("/state/{state}")
    @Operation(summary = "Fetch devices by state")
    public ResponseEntity<List<DeviceDto>> getByState(@PathVariable DeviceState state) {
        return ResponseEntity.ok(service.getByState(state));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a device by ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

