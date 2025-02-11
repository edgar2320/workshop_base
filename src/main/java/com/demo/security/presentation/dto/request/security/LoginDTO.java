package com.demo.security.presentation.dto.request.security;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "El username es requerido")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        String password
) {
}
