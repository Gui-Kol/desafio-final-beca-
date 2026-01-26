package com.nttdata.infra.service.method;

import com.nttdata.domain.bank.BankDto;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import com.nttdata.infra.service.PayBalance;

public class Cash {
    private final PayBalance payBalance;
    private final MockApi mockApi;

    public Cash(PayBalance payBalance, MockApi mockApi) {
        this.payBalance = payBalance;
        this.mockApi = mockApi;
    }


    public void pay(Transaction trasaction) {
        var sourceId = trasaction.getSourceAccountId();
        var destinationId = trasaction.getDestinationAccountId();
        BankDto sourceBank = mockApi.get(sourceId);
        BankDto destinationBank = mockApi.get(destinationId);

        if (trasaction.getType().equals(TransactionType.DEPOSIT)) {
            payBalance.deposit(trasaction, sourceBank, destinationBank);
        } else if (trasaction.getType().equals(TransactionType.PURCHASE)) {
            payBalance.setCompleted(trasaction);
        } else if (trasaction.getType().equals(TransactionType.WITHDRAWAL)) {
            payBalance.withdrawal(trasaction, sourceBank);
        } else {
            payBalance.setCompleted(trasaction);
        }


    }
}

