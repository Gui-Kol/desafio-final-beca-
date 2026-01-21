package com.nttdata.config;

import com.nttdata.application.usecase.Pay;
import com.nttdata.infra.gateway.TransactionRepositoryJpa;
import com.nttdata.infra.service.ClientValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {
    @Bean
    public Pay pay(TransactionRepositoryJpa repository) {
        return new Pay(repository);
    }

    @Bean
    public TransactionRepositoryJpa repositoryJpa(ClientValidationService clientValidationService) {
        return new TransactionRepositoryJpa(clientValidationService);
    }

}
