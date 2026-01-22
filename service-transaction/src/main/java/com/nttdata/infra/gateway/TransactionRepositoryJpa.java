package com.nttdata.infra.gateway;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.persitence.client.TransactionRepositoryEntity;
import com.nttdata.infra.service.ClientValidationService;
import org.springframework.kafka.core.KafkaTemplate;

public class TransactionRepositoryJpa implements TransactionRepository {
    private final ClientValidationService clientValidationService;
    private final TransactionRepositoryEntity repositoryEntity;
    private final TransactionMapper mapper;
    private final KafkaTemplate<String,Transaction> kafkaTemplate;

    public TransactionRepositoryJpa(ClientValidationService clientValidationService, TransactionRepositoryEntity repositoryEntity, TransactionMapper mapper, KafkaTemplate kafkaTemplate) {
        this.clientValidationService = clientValidationService;
        this.repositoryEntity = repositoryEntity;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public boolean validClient(Long sourceAccountId) {
        boolean response = clientValidationService.checkClientExistence(sourceAccountId);
        System.out.println(response);
        return response;
    }

    @Override
    public Transaction saveTransactionPending(Transaction transaction) {
        System.out.println(transaction.getStatus());
        var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
        return mapper.toTransaction(response);
    }

}
