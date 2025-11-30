package com.bank.domain.exception;

public final class CuentaNoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String numeroCuenta;

    public CuentaNoEncontradaException(String numeroCuenta) {
        super("Cuenta no encontrada: " + numeroCuenta);
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
}
