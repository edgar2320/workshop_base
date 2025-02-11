package com.demo.security.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDTO {
    int status;
    String message;
    LocalDateTime timestamp;
    List<String> details;
}
