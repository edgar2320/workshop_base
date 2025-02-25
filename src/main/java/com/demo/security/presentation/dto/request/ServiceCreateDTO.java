package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceCreateDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull boolean status,
        @NotNull double price,
        @NotNull Long categoryId
) {
}
