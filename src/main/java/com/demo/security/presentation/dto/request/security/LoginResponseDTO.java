package com.demo.security.presentation.dto.request.security;

import com.demo.security.presentation.dto.response.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginResponseDTO {
    String accessToken;
    String refreshToken;
    String tokenType;    // Generalmente "Bearer"
    Long expiresIn;      // Tiempo de expiración en segundos
    UserDTO user;     // Información del usuario
}
