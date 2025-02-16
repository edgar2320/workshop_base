package com.demo.security.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
