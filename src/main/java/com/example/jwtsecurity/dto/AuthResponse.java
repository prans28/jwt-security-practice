package com.example.jwtsecurity.dto;

public record AuthResponse(String accessToken, String refreshToken, String tokenType, long accessExpiresInSeconds,
                           long refreshExpiresInSeconds) {
}
