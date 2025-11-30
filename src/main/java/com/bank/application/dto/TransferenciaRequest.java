package com.bank.application.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TransferenciaRequest", description = "Solicitud para transferir entre cuentas")
public class TransferenciaRequest {

    @Schema(description = "Cuenta origen", example = "1234567890", required = true)
    private String origen;

    @Schema(description = "Cuenta destino", example = "9876543210", required = true)
    private String destino;

    @Schema(description = "Monto a transferir", example = "300.00", required = true)
    private BigDecimal monto;

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
