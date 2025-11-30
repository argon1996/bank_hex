package com.bank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "AuthResponse", description = "Respuesta de autenticación con token JWT")
public class AuthResponse {
    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "Usuario autenticado", example = "admin")
    private String username;

    @Schema(description = "Rol del usuario", example = "ROLE_ADMIN")
    private String role;

    @Schema(description = "Fecha/hora de expiración del token", example = "2025-11-30T20:56:53Z")
    private Instant expiresAt;

    public AuthResponse() {
    }

    public AuthResponse(String token, String username, String role, Instant expiresAt) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}
