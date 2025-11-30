package com.bank.application.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MovimientoRequest", description = "Monto a mover en la cuenta")
public class MovimientoRequest {

    @Schema(description = "Monto a acreditar/debitar", example = "500.00", required = true)
    private BigDecimal monto;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
