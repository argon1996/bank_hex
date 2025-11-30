package com.bank.application.mapper;

import com.bank.application.dto.CuentaDTO;
import com.bank.domain.model.Cuenta;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T14:55:44-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Eclipse Adoptium)"
)
@Component
public class CuentaMapperImpl implements CuentaMapper {

    @Override
    public CuentaDTO toDTO(Cuenta cuenta) {
        if ( cuenta == null ) {
            return null;
        }

        String numero = null;
        BigDecimal saldo = null;

        numero = cuenta.getNumero();
        saldo = cuenta.getSaldo();

        CuentaDTO cuentaDTO = new CuentaDTO( numero, saldo );

        return cuentaDTO;
    }

    @Override
    public Cuenta toEntity(CuentaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setNumero( dto.getNumero() );
        cuenta.setSaldo( dto.getSaldo() );

        return cuenta;
    }
}
