package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.infra.exception.TransactionException;

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
            throw new TransactionException("The client is invalid or inactive!");
        }
    }

    public Transaction transactionExternal(Transaction transaction) {
        boolean sourceAccountValid = repository.validClient(transaction.getSourceAccountId());
        if (sourceAccountValid){
            return repository.saveTransactionCompleted(transaction);
        }else {
            throw new TransactionException("The client is invalid or inactive!");
        }
    }
}
