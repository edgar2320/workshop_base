package com.demo.security.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private boolean status;
    private double price;
    private CategoryDTO category;
}
