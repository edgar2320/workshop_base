package com.demo.security.presentation.dto.response;

import com.demo.security.persistence.entities.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String plate;
    private String mark;
    private String model;
    private int year;
    private ClientDTO client;
}
