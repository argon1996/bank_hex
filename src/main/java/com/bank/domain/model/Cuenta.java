package com.bank.domain.model;

import java.math.BigDecimal;

public class Cuenta {

    private Long id;
    private String numero;
    private BigDecimal saldo;

    public Cuenta() {} // ✅ constructor por defecto requerido

    public Cuenta(Long id, String numero, BigDecimal saldo) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de cuenta no puede estar vacío");
        }
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }

        this.id = id;
        this.numero = numero;
        this.saldo = saldo;
    }

    public Cuenta(String numero, BigDecimal saldo) {
        this(null, numero, saldo);
    }

    public Long getId() { return id; }
    public String getNumero() { return numero; }
    public BigDecimal getSaldo() { return saldo; }

    // ✅ Agrega estos setters para MapStruct
    public void setNumero(String numero) { this.numero = numero; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public void depositar(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        this.saldo = this.saldo.add(monto);
    }

    public void retirar(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (this.saldo.compareTo(monto) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }
        this.saldo = this.saldo.subtract(monto);
    }
}
