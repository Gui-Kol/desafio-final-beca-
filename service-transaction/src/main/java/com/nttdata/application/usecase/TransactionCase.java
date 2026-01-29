package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;

public class TransactionCase {
    private final TransactionRepository repository;

    public TransactionCase(TransactionRepository repository) {
        this.repository = repository;
    }


    public Transaction transaction(Transaction transaction) {
        repository.validClient(transaction.getSourceAccountId());
        repository.validClient(transaction.getDestinationAccountId());
        return repository.saveTransactionPending(transaction);
    }

    public Transaction transactionExternal(Transaction transaction) {
        repository.validClient(transaction.getSourceAccountId());
        return repository.saveTransactionCompleted(transaction);
    }
}
