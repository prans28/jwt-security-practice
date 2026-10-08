package com.example.jwtsecurity.dto;

import com.example.jwtsecurity.entity.Role;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(@NotNull Role role) {
}
