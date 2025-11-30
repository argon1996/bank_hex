package com.bank.application.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CrearCuentaRequest", description = "Datos para crear una cuenta")
public class CrearCuentaRequest {

    @Schema(description = "Número de cuenta", example = "1234567890", required = true)
    private String numero;

    @Schema(description = "Saldo inicial de la cuenta", example = "1000.00", required = true)
    private BigDecimal saldoInicial;

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}
