package com.demo.security.presentation.dto.request.security;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(
        @NotBlank(message = "El refresh token es requerido")
        String refreshToken
) {
}
