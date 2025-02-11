package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RolCreateDTO(
        @NotBlank String name,
        String description,
        Set<Long> permissionIds
) {
}
