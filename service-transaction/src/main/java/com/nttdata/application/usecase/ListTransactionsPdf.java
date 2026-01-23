package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;

import java.util.List;

public class ListTransactionsPdf {
    private final TransactionRepository repository;
    public ListTransactionsPdf(TransactionRepository repository) {
        this.repository = repository;
    }

    public void create(Long clientId) {
        repository.createPdf(clientId);
    }
}
