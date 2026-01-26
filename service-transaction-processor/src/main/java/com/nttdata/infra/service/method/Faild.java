package com.nttdata.infra.service.method;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.StatusTransaction;

public class Faild {
    private final TransactionRepository transactionRepository;

    public Faild(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void fail(Transaction transaction) {
        transaction.setStatus(StatusTransaction.FAILED);
        transactionRepository.update(transaction);
    }

}
