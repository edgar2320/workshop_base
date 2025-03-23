package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.ServiceCreateDTO;
import com.demo.security.presentation.dto.response.ServiceDTO;
import com.demo.security.service.interfaces.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @GetMapping("/list")
    public ResponseEntity<Page<ServiceDTO>> listService(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(serviceService.getServices(pageable));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ServiceDTO> getService(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getService(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceDTO> createService(@Valid @RequestBody ServiceCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceService.createService(dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceDTO> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceCreateDTO dto
    ) {
        return ResponseEntity.ok(serviceService.updateService(id, dto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
