package com.nttdata.application.repository;

import com.nttdata.domain.transaction.Transaction;

import java.util.List;

public interface TransactionRepository {
    boolean validClient(Long sourceAccountId);


    Transaction saveTransactionPending(Transaction transaction);

    Transaction cancelTransaction(Long transactionId);

    List<Transaction> listByClientId(Long clientId);

    void createPdf(Long clientId);

    void update(Transaction trasaction);
}
