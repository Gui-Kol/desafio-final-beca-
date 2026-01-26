package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayCreditInterface;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

public class CreditCard {
    private final PayCreditInterface payCredit;

    public CreditCard(PayCreditInterface payCredit) {
        this.payCredit = payCredit;
    }


    public void pay(Transaction transaction) {

        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            payCredit.setFaild(transaction);

        } else if (transaction.getType().equals(TransactionType.PURCHASE)) {
            payCredit.purchaseTransfer(transaction);

        } else if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payCredit.setFaild(transaction);

        } else { //TRANSFER
            payCredit.purchaseTransfer(transaction);
        }
    }
}
