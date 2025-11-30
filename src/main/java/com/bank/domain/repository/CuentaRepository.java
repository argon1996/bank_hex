package com.bank.domain.repository;

import com.bank.domain.model.Cuenta;

import java.util.Optional;

public interface CuentaRepository {

    Cuenta guardar(Cuenta cuenta);

    
    Optional<Cuenta> buscarPorNumero(String numero);
}
