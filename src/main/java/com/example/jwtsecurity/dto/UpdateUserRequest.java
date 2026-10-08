package com.example.jwtsecurity.dto;

import jakarta.validation.constraints.*;

public record UpdateUserRequest(@NotBlank String name, @Email @NotBlank String email) {
}
