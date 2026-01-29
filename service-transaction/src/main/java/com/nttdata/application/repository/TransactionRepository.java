package com.nttdata.application.repository;

import com.nttdata.domain.transaction.Transaction;

import java.util.List;

public interface TransactionRepository {
    boolean validClient(Long AccountId);


    Transaction saveTransactionPending(Transaction transaction);

    Transaction cancelTransaction(Long transactionId);

    List<Transaction> listByClientId(Long clientId, int day);

    void createPdf(Long clientId, int day);

    void update(Transaction trasaction);

    Transaction saveTransactionCompleted(Transaction transaction);
}
