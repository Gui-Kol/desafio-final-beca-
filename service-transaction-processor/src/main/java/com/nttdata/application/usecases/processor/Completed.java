package com.nttdata.application.usecases.processor;

import com.nttdata.application.interfaces.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.StatusTransaction;

public class Completed {
    private final TransactionRepository transactionRepository;

    public Completed(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void complete(Transaction transaction) {
        transaction.setStatus(StatusTransaction.COMPLETED);
        transactionRepository.update(transaction);
    }
}
