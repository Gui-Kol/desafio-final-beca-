package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayBalanceInterface;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

public class Cash {
    private final PayBalanceInterface payBalance;

    public Cash(PayBalanceInterface payBalance) {
        this.payBalance = payBalance;
    }


    public void pay(Transaction trasaction) {

        if (trasaction.getType().equals(TransactionType.DEPOSIT)) {
            payBalance.deposit(trasaction);
        } else if (trasaction.getType().equals(TransactionType.PURCHASE)) {
            payBalance.setCompleted(trasaction);
        } else if (trasaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payBalance.withdrawal(trasaction);
        } else {
            payBalance.setCompleted(trasaction);
        }


    }
}

