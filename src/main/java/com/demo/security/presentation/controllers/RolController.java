package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.RolCreateDTO;
import com.demo.security.presentation.dto.response.RolDTO;
import com.demo.security.service.interfaces.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
public class RolController {
    private final RolService rolService;

    @PostMapping("/create")
    public ResponseEntity<RolDTO> createRol(@Valid @RequestBody RolCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolService.createRol(dto));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<RolDTO> getRol(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.getRol(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RolDTO>> listRoles() {
        return ResponseEntity.ok(rolService.listRoles());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RolDTO> updateRol(
            @PathVariable Long id,
            @Valid @RequestBody RolCreateDTO dto) {
        return ResponseEntity.ok(rolService.updateRol(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }
}
