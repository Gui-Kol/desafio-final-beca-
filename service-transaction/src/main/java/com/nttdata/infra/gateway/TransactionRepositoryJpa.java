package com.nttdata.infra.gateway;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.infra.exception.newexception.ClientNotExistsException;
import com.nttdata.infra.exception.newexception.TransactionException;
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
    public boolean validClient(Long AccountId) {
        return clientValidationService.checkClientExistence(AccountId);
    }

    @Override
    public Transaction saveTransactionPending(Transaction transaction) {
            var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
            return mapper.toTransaction(response);
    }

    @Override
    public Transaction cancelTransaction(Long transactionId) {
        if (repositoryEntity.existsById(transactionId)) {
            var entity = repositoryEntity.getReferenceById(transactionId);
            entity.cancelTransaction();
            return mapper.toTransaction(repositoryEntity.save(entity));
        } else {
            throw new TransactionException("There is no transition with this ID " + transactionId);
        }
    }

    @Override
    public List<Transaction> listByClientId(Long clientId, int day) {
        if (day <= 0){throw new TransactionException("It's not possible to generate a statement for a given number of days!");}

        if (repositoryEntity.existsBySourceAccountId(clientId) || repositoryEntity.existsByDestinationAccountId(clientId)) {
            LocalDateTime timeLimit = LocalDateTime.now().minusDays(day);

            List<TransactionEntity> listDestinationAccountId = repositoryEntity.findByDestinationAccountId(clientId);
            List<TransactionEntity> response = repositoryEntity.findBySourceAccountId(clientId);

            response.addAll(listDestinationAccountId);

            return response.stream()
                    .filter(e -> e.getTransactionDate().isAfter(timeLimit))
                    .sorted(Comparator.comparing(TransactionEntity::getTransactionDate).reversed())
                    .map(mapper::toTransaction)
                    .collect(Collectors.toList());
        } else {
            throw new ClientNotExistsException("The client with ID " + clientId + " does not exist!");
        }
    }

    @Override
    public void createPdf(Long clientId, int day) {
        List<Transaction> list = listByClientId(clientId, day);
        pdfGenerator.generate(list);
    }

    public void update(Transaction trasaction) {
        try {
            var entity = repositoryEntity.getReferenceById(trasaction.getId());
            entity.update(trasaction);
            repositoryEntity.save(entity);
        }catch (NullPointerException e){
            throw new TransactionException("The transaction with ID " + trasaction.getId() + " does not exist!");
        }

    }

    @Override
    public Transaction saveTransactionCompleted(Transaction transaction) {
        transaction.setStatus(StatusTransaction.EXTERNAL);
        var response = repositoryEntity.save(mapper.toTransactionEntity(transaction));
        return mapper.toTransaction(response);
    }

}
