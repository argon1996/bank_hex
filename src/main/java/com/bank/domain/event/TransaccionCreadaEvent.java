package com.bank.domain.event;

import com.bank.domain.model.Transaccion;
import org.springframework.context.ApplicationEvent;

public class TransaccionCreadaEvent extends ApplicationEvent {

    private final Transaccion transaccion;

    public TransaccionCreadaEvent(Object source, Transaccion transaccion) {
        super(source);
        this.transaccion = transaccion;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }
}
