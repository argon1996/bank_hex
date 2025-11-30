package com.bank.domain.repository;

import com.bank.domain.model.Transaccion;
import java.util.List;

public interface TransaccionRepository {

    Transaccion guardar(Transaccion transaccion);

    List<Transaccion> buscarPorNumeroCuenta(String numeroCuenta);
}
