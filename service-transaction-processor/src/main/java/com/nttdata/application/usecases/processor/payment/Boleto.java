package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayBalanceInterface;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

public class Boleto {
    private final PayBalanceInterface payBalance;

    public Boleto(PayBalanceInterface payBalance) {
        this.payBalance = payBalance;
    }


    public void pay(Transaction transaction) {

        if (transaction.getType().equals(TransactionType.DEPOSIT) || transaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payBalance.setFaild(transaction);

        } else {  // TRANSFER or PURCHASE
            payBalance.purchaseTransfer(transaction);
        }
    }
}

