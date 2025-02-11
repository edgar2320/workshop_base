package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserCreateDTO(
        @NotBlank String username,
        @NotBlank String password,
        @Email String email,
        @NotEmpty Set<Long> rolesIds
) {
}
