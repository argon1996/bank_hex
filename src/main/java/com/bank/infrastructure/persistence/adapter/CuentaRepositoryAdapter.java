package com.bank.infrastructure.persistence.adapter;

import com.bank.domain.model.Cuenta;
import com.bank.domain.repository.CuentaRepository;
import com.bank.infrastructure.persistence.entity.CuentaEntity;
import com.bank.infrastructure.persistence.jpa.CuentaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CuentaRepositoryAdapter implements CuentaRepository {

    private final CuentaJpaRepository jpaRepository;

    public CuentaRepositoryAdapter(CuentaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cuenta guardar(Cuenta cuenta) {

        CuentaEntity entity;

        // --- CUENTA NUEVA ---
        if (cuenta.getId() == null) {
            entity = new CuentaEntity();
            entity.setNumero(cuenta.getNumero());
            entity.setSaldo(cuenta.getSaldo());
        }
        // --- ACTUALIZAR CUENTA EXISTENTE ---
        else {
            entity = jpaRepository.findById(cuenta.getId())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada al actualizar"));

            entity.setNumero(cuenta.getNumero());
            entity.setSaldo(cuenta.getSaldo());
        }

        CuentaEntity saved = jpaRepository.save(entity);

        return new Cuenta(
                saved.getId(),
                saved.getNumero(),
                saved.getSaldo()
        );
    }

    @Override
    public Optional<Cuenta> buscarPorNumero(String numero) {
        return jpaRepository.findByNumero(numero)
                .map(e -> new Cuenta(e.getId(), e.getNumero(), e.getSaldo()));
    }
}
