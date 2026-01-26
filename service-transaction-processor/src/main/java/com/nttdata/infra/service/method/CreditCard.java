package com.nttdata.infra.service.method;

import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import com.nttdata.domain.bank.BankDto;
import com.nttdata.infra.service.PayCredit;
import com.nttdata.infra.service.Validate;

public class CreditCard {
    private final PayCredit payCredit;
    private final Validate validate;

    public CreditCard(PayCredit payCredit, Validate validate) {
        this.payCredit = payCredit;
        this.validate = validate;
    }

    public void pay(Transaction transaction) {
        var sourceId = transaction.getSourceAccountId();
        var destinationId = transaction.getDestinationAccountId();
        BankDto sourceBank = validate.valid(sourceId);
        BankDto destinationBank = validate.valid(destinationId);

        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            payCredit.setFaild(transaction);

        } else if (transaction.getType().equals(TransactionType.PURCHASE)) {
            payCredit.purchaseTransfer(transaction, sourceBank, destinationBank);

        } else if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payCredit.setFaild(transaction);

        } else { //TRANSFER
            payCredit.purchaseTransfer(transaction, sourceBank, destinationBank);
        }
    }
}
