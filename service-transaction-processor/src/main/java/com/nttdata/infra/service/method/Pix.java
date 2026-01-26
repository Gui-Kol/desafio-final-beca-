package com.nttdata.infra.service.method;

import com.nttdata.domain.bank.BankDto;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import com.nttdata.infra.service.PayBalance;

public class Pix {
    private final PayBalance payBalance;
    private final MockApi mockApi;

    public Pix(PayBalance payBalance, MockApi mockApi) {
        this.payBalance = payBalance;
        this.mockApi = mockApi;
    }


    public void pay(Transaction transaction) {
        var sourceId = transaction.getSourceAccountId();
        var destinationId = transaction.getDestinationAccountId();
        BankDto sourceBank = mockApi.get(sourceId);
        BankDto destinationBank = mockApi.get(destinationId);

        if (transaction.getType().equals(TransactionType.DEPOSIT)) {
            payBalance.setFaild(transaction);

        } else if (transaction.getType().equals(TransactionType.PURCHASE)) {
            payBalance.purchaseTransfer(transaction, sourceBank, destinationBank);

        } else if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payBalance.withdrawal(transaction, sourceBank);

        } else { //TRANSFER
            payBalance.purchaseTransfer(transaction, sourceBank, destinationBank);
        }


    }
}
