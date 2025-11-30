package com.bank.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionDTO {

    private BigDecimal monto;
    private String tipo;
    private LocalDateTime fecha;

    public TransaccionDTO(BigDecimal monto, String tipo, LocalDateTime fecha) {
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
