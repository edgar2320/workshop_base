package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.VehicleCreateDTO;
import com.demo.security.presentation.dto.response.VehicleDTO;
import com.demo.security.service.interfaces.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<VehicleDTO>> listVehicles(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(vehicleService.listVehicle(pageable));
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.create(dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleCreateDTO dto
    ) {
        return ResponseEntity.ok(vehicleService.update(id, dto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
