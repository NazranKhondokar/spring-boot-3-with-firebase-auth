package com.nazran.springboot3firebseauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for login request.
 */
@Data
public class LoginRequest {
    @NotBlank(message = "Token is required.")
    private String idToken;
}

