package com.nttdata.application.repository;

import com.nttdata.domain.transaction.Transaction;

public interface TransactionRepository {
    boolean validClient(Long sourceAccountId);


    Transaction saveTransactionPending(Transaction transaction);
}
