package com.bank.domain.exception;

public final class SaldoInsuficienteException extends RuntimeException {

    private static final String DEFAULT_MESSAGE =
            "La cuenta no tiene fondos suficientes para completar la operación.";

    public SaldoInsuficienteException() {
        super(DEFAULT_MESSAGE);
    }
}
