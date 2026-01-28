package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;

import java.util.List;

public class ListTransactions {
    private final TransactionRepository repository;

    public ListTransactions(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> byClientId(Long clientId, int day){
        return repository.listByClientId(clientId,day);
    }
}
