package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.Transaction;

public class Pay {
    private final TransactionRepository transactionRepository;

    public Pay(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction transaction(Long sourceAccountId){
        boolean sourceAccountValid = transactionRepository.validClient(sourceAccountId);
        if (!sourceAccountValid){
            throw new RuntimeException("The client is invalid or inactive!");
        }
        return new Transaction();

    }

}
