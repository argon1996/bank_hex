package com.bank.infrastructure.config;

import com.bank.application.mapper.CuentaMapper;
import com.bank.application.usecase.CuentaUseCase;
import com.bank.domain.repository.CuentaRepository;
import com.bank.domain.repository.TransaccionRepository;
import com.bank.domain.service.CuentaService;

import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CuentaService cuentaService(CuentaRepository cRepo,
            TransaccionRepository tRepo,
            ApplicationEventPublisher eventPublisher) {
        // 👈 ahora sí pasa el publisher
        return new CuentaService(cRepo, tRepo, eventPublisher);
    }

    @Bean
    public CuentaMapper cuentaMapper() {
        return Mappers.getMapper(CuentaMapper.class);
    }

}
