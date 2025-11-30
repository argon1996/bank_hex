package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.Transaccion;
import com.bank.domain.repository.TransaccionRepository;
import com.bank.infrastructure.persistence.entity.TransaccionEntity;
import com.bank.infrastructure.persistence.jpa.TransaccionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransaccionRepositoryAdapter implements TransaccionRepository {

    private final TransaccionJpaRepository jpa;

    public TransaccionRepositoryAdapter(TransaccionJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Transaccion guardar(Transaccion transaccion) {
        TransaccionEntity entity = new TransaccionEntity();

        entity.setNumeroCuenta(transaccion.getNumeroCuenta());
        entity.setMonto(transaccion.getMonto());
        entity.setTipo(transaccion.getTipo());
        entity.setFecha(transaccion.getFecha());

        TransaccionEntity saved = jpa.save(entity);

        return new Transaccion(
                saved.getId(),
                saved.getNumeroCuenta(),
                saved.getMonto(),
                saved.getTipo(),
                saved.getFecha()
        );
    }

    @Override
    public List<Transaccion> buscarPorNumeroCuenta(String numeroCuenta) {
        return jpa.findByNumeroCuenta(numeroCuenta)
                .stream()
                .map(e -> new Transaccion(
                        e.getId(),
                        e.getNumeroCuenta(),
                        e.getMonto(),
                        e.getTipo(),
                        e.getFecha()
                ))
                .collect(Collectors.toList());
    }
}
