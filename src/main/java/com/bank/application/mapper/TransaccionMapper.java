package com.bank.application.mapper;

import com.bank.application.dto.TransaccionDTO;
import com.bank.domain.model.Transaccion;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TransaccionMapper {

    TransaccionDTO toDTO(Transaccion transaccion);
}
