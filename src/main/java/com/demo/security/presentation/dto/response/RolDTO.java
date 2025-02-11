package com.demo.security.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class RolDTO {
    private Long id;
    private String name;
    private String description;
    private Set<PermissionDTO> permissions;
}
