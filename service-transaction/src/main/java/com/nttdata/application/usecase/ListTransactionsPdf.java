package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;

public class ListTransactionsPdf {
    private final TransactionRepository repository;
    public ListTransactionsPdf(TransactionRepository repository) {
        this.repository = repository;
    }

    public void create(Long clientId, int day) {
        repository.createPdf(clientId, day);
    }
}
