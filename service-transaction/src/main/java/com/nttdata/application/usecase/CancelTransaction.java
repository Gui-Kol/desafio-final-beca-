package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;

public class CancelTransaction {
    private final TransactionRepository repository;
    public CancelTransaction(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction cancel(Long transactionId) {
        return repository.cancelTransaction(transactionId);
    }
}
