package com.neighbourly.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Invalid email format") @NotBlank String email,
        @NotBlank(message = "Password cannot be blank") String password
) {}
