package com.nttdata.config;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.application.usecase.CancelTransaction;
import com.nttdata.application.usecase.TransactionCase;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.infra.gateway.TransactionMapper;
import com.nttdata.infra.gateway.TransactionRepositoryJpa;
import com.nttdata.infra.persitence.client.TransactionRepositoryEntity;
import com.nttdata.infra.service.ClientValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class TransactionConfig {
    @Bean
    public TransactionCase pay(TransactionRepositoryJpa repository) {
        return new TransactionCase(repository);
    }

    @Bean
    public TransactionRepositoryJpa repositoryJpa(ClientValidationService clientValidationService, TransactionRepositoryEntity repository,
                                                  TransactionMapper mapper, KafkaTemplate kafkaTemplate) {
        return new TransactionRepositoryJpa(clientValidationService, repository, mapper, kafkaTemplate);
    }

    @Bean
    public TransactionFactory transactionFactory() {
        return new TransactionFactory();
    }

    @Bean
    public TransactionMapper transactionMapper() {
        return new TransactionMapper();
    }

    @Bean
    public CancelTransaction cancelTransaction(TransactionRepository repository) {
        return new CancelTransaction(repository);
    }

}
