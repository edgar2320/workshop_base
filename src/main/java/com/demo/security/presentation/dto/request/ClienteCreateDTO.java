package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteCreateDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String phone
) {

}
