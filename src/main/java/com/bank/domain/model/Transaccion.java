package com.bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {

    private Long id;
    private String numeroCuenta;
    private BigDecimal monto;
    private TipoTransaccion tipo;
    private LocalDateTime fecha;

    public Transaccion(Long id, String numeroCuenta, BigDecimal monto, TipoTransaccion tipo, LocalDateTime fecha) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public BigDecimal getMonto() { return monto; }
    public TipoTransaccion getTipo() { return tipo; }
    public LocalDateTime getFecha() { return fecha; }
}
