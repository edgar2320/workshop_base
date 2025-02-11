package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PermissionCreateDTO(
        @NotBlank String name,
        String description
) {
}
