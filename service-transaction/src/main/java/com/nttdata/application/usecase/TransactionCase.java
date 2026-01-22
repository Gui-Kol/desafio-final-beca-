package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;

public class TransactionCase {
    private final TransactionRepository repository;

    public TransactionCase(TransactionRepository repository) {
        this.repository = repository;
    }


    public Transaction transaction(Transaction transaction){

        boolean sourceAccountValid = repository.validClient(transaction.getSourceAccountId());
        boolean destinationAccountValid = repository.validClient(transaction.getDestinationAccountId());
        if (sourceAccountValid && destinationAccountValid){

            return repository.saveTransactionPending(transaction);

        }else {
            throw new RuntimeException("The client is invalid or inactive!");
        }
    }

}
