package com.bank.application.mapper;

import com.bank.application.dto.CuentaDTO;
import com.bank.domain.model.Cuenta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaDTO toDTO(Cuenta cuenta);
    Cuenta toEntity(CuentaDTO dto);
}
