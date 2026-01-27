package com.nttdata.application.interfaces;

import com.nttdata.domain.transaction.Transaction;

public interface TransactionRepository {
    void update(Transaction transaction);
}
