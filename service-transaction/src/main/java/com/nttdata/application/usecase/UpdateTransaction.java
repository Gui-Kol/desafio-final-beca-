package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;

public class UpdateTransaction {
    private final TransactionRepository repository;

    public UpdateTransaction(TransactionRepository repository) {
        this.repository = repository;
    }

    public void update(Transaction transaction){
        repository.update(transaction);
    }

}
