package com.nttdata.infra.gateway;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.infra.persistence.client.TransactionEntity;
import com.nttdata.infra.persistence.client.TransactionRepositoryEntity;
import com.nttdata.infra.service.ClientValidationService;
import com.nttdata.infra.service.pdf.PdfGenerator;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepositoryJpa implements TransactionRepository {
    private final ClientValidationService clientValidationService;
    private final TransactionRepositoryEntity repositoryEntity;
    private final PdfGenerator pdfGenerator;
    private final TransactionMapper mapper;

    public TransactionRepositoryJpa(ClientValidationService clientValidationService, TransactionRepositoryEntity repositoryEntity, PdfGenerator pdfGeneretor, TransactionMapper mapper) {
        this.clientValidationService = clientValidationService;
        this.repositoryEntity = repositoryEntity;
        this.pdfGenerator = pdfGeneretor;
        this.mapper = mapper;
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
            var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
            return mapper.toTransaction(response);
        } else {
            return saveTransactionCompleted(transaction);
        }
    }

    @Override
    public Transaction cancelTransaction(Long transactionId) {
        if (repositoryEntity.existsById(transactionId)) {
            var entity = repositoryEntity.getReferenceById(transactionId);

            if (entity.getMethod().equals(PaymentMethod.CASH)) {
                throw new RuntimeException("It is not possible to cancel a transaction made in cash!");
            }

            entity.cancelTransaction();
            return mapper.toTransaction(repositoryEntity.save(entity));
        } else {
            throw new RuntimeException("There is no transition with this ID " + transactionId);
        }
    }

    @Override
    public List<Transaction> listByClientId(Long clientId) {
        if (repositoryEntity.existsBySourceAccountId(clientId) || repositoryEntity.existsByDestinationAccountId(clientId)) {
            LocalDateTime timeLimit = LocalDateTime.now().minusHours(24);

            List<TransactionEntity> listDestinationAccountId = repositoryEntity.findByDestinationAccountId(clientId);
            List<TransactionEntity> response = repositoryEntity.findBySourceAccountId(clientId);

            response.addAll(listDestinationAccountId);

            return response.stream()
                    .filter(e -> e.getTransactionDate().isAfter(timeLimit))
                    .sorted(Comparator.comparing(TransactionEntity::getTransactionDate).reversed())
                    .map(mapper::toTransaction)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("The client with ID " + clientId + " does not exist!");
        }
    }

    @Override
    public void createPdf(Long clientId) {
        List<Transaction> list = listByClientId(clientId);
        pdfGenerator.generate(list);
    }

    private Transaction saveTransactionCompleted(Transaction transaction) {
        transaction.setStatus(StatusTransaction.COMPLETED);
        var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
        return mapper.toTransaction(response);
    }

}
