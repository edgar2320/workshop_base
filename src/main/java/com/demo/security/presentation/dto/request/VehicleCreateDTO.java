package com.demo.security.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleCreateDTO(
        @NotBlank String plate,
        @NotBlank String mark,
        @NotBlank String model,
        @NotNull int year,
        @NotNull Long clientId
) {

}
