package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.Email;

import java.util.Set;

public record UserUpdateDTO(
        @Email String email,
        Set<Long> rolesIds
) {
}
