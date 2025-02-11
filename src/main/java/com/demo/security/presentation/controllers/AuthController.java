package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.security.LoginDTO;
import com.demo.security.presentation.dto.request.security.RefreshTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        // Este endpoint se implementará cuando agreguemos seguridad
        return ResponseEntity.ok("Login endpoint - pendiente implementación");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshTokenDTO dto) {
        // Este endpoint se implementará cuando agreguemos seguridad
        return ResponseEntity.ok("Refresh token endpoint - pendiente implementación");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Este endpoint se implementará cuando agreguemos seguridad
        return ResponseEntity.ok().build();
    }
}
