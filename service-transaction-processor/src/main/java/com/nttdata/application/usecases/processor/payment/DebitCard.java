package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayBalanceInterface;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

public class DebitCard {
    private final PayBalanceInterface payBalance;

    public DebitCard(PayBalanceInterface payBalance) {
        this.payBalance = payBalance;
    }


    public void pay(Transaction transaction) {

        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            payBalance.setFaild(transaction);

        } else if (transaction.getType().equals(TransactionType.PURCHASE)) {
            payBalance.purchaseTransfer(transaction);

        } else if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payBalance.withdrawal(transaction);

        } else { //TRANSFER
            payBalance.setFaild(transaction);
        }


    }
}
