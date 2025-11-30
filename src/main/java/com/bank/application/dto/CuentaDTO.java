package com.bank.application.dto;

import java.math.BigDecimal;

public class CuentaDTO {

    private String numero;
    private BigDecimal saldo;

    public CuentaDTO(String numero, BigDecimal saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
