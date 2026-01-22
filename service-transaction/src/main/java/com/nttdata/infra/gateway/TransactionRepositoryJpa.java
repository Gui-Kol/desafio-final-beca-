package com.nttdata.infra.gateway;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.infra.persitence.client.TransactionRepositoryEntity;
import com.nttdata.infra.service.ClientValidationService;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

public class TransactionRepositoryJpa implements TransactionRepository {
    private final ClientValidationService clientValidationService;
    private final TransactionRepositoryEntity repositoryEntity;
    private final TransactionMapper mapper;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

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
        if (!transaction.getMethod().equals(PaymentMethod.CASH)) {
            if (transaction.getCurrency().equals("BRL")){
                transaction.setConvertedValue(transaction.getValue());
                transaction.setAppliedExchangeRate(BigDecimal.valueOf(1));
            }
            var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
            return mapper.toTransaction(response);
        }else {
            return saveTransactionCompleted(transaction);
        }
    }

    @Override
    public Transaction cancelTransaction(Long transactionId) {
        if (repositoryEntity.existsById(transactionId)){
            var entity = repositoryEntity.getReferenceById(transactionId);

            if (entity.getMethod().equals(PaymentMethod.CASH)){
                throw new RuntimeException("It is not possible to cancel a transaction made in cash!");
            }

            entity.cancelTransaction();
            return mapper.toTransaction(repositoryEntity.save(entity));
        }else {
            throw new RuntimeException("There is no transition with this ID " + transactionId);
        }
    }

    private Transaction saveTransactionCompleted(Transaction transaction){
        transaction.setStatus(StatusTransaction.COMPLETED);
        var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
        return mapper.toTransaction(response);
    }

}
