package com.bank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "Credenciales para autenticación")
public class LoginRequest {
    @Schema(description = "Usuario", example = "admin", required = true)
    private String username;

    @Schema(description = "Contraseña", example = "password", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
