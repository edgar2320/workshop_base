package com.demo.security.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean status;
    private Set<RolDTO> roles;
    private LocalDateTime dateCreate;
    private LocalDateTime dateUpdate;
}
