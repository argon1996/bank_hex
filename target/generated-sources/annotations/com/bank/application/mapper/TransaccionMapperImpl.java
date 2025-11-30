package com.bank.application.mapper;

import com.bank.application.dto.TransaccionDTO;
import com.bank.domain.model.Transaccion;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T14:55:44-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Eclipse Adoptium)"
)
@Component
public class TransaccionMapperImpl implements TransaccionMapper {

    @Override
    public TransaccionDTO toDTO(Transaccion transaccion) {
        if ( transaccion == null ) {
            return null;
        }

        BigDecimal monto = null;
        String tipo = null;
        LocalDateTime fecha = null;

        monto = transaccion.getMonto();
        if ( transaccion.getTipo() != null ) {
            tipo = transaccion.getTipo().name();
        }
        fecha = transaccion.getFecha();

        TransaccionDTO transaccionDTO = new TransaccionDTO( monto, tipo, fecha );

        return transaccionDTO;
    }
}
