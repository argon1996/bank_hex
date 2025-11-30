package com.bank.infrastructure.persistence.jpa;

import com.bank.infrastructure.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {

    Optional<CuentaEntity> findByNumero(String numero);
}


