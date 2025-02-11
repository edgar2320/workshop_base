package com.demo.security.presentation.controllers;
import com.demo.security.presentation.dto.request.PermissionCreateDTO;
import com.demo.security.presentation.dto.response.PermissionDTO;
import com.demo.security.service.interfaces.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @PostMapping("/create")
    public ResponseEntity<PermissionDTO> createPermission(@Valid @RequestBody PermissionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.createPermission(dto));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermission(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PermissionDTO>> listPermissions() {
        return ResponseEntity.ok(permissionService.listPermission());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionCreateDTO dto) {
        return ResponseEntity.ok(permissionService.updatePermission(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
