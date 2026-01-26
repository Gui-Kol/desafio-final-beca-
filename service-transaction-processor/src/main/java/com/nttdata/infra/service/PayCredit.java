package com.nttdata.infra.service;

import com.nttdata.domain.bank.Bank;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.bank.BankDto;
import com.nttdata.infra.service.method.Completed;
import com.nttdata.infra.service.method.Faild;
import com.nttdata.infra.service.method.MockApi;

public class PayCredit {
    private final Faild faild;
    private final Completed completed;
    private final MockApi mockApi;


    String url = "http://6973d075b5f46f8b5828534e.mockapi.io/mockapi/bank/";

    public PayCredit(Faild faild, Completed completed, MockApi mockApi) {
        this.faild = faild;
        this.completed = completed;
        this.mockApi = mockApi;
    }


    public void purchaseTransfer(Transaction transaction, BankDto sourceBank, BankDto destinationBank) {
        var transactionValue = transaction.getValue().doubleValue();

        if (sourceBank.credit() >= transactionValue) {
            var remainingSource = sourceBank.credit() - transactionValue;
            var remainingDestination = destinationBank.balance() + transactionValue;

            Bank sourceBankUpdated = new Bank(sourceBank.account(), sourceBank.clientId(), sourceBank.balance(), remainingSource, sourceBank.id());
            mockApi.put(url, sourceBankUpdated);

            Bank destinationBankUpdated = new Bank(destinationBank.account(), destinationBank.clientId(), remainingDestination, destinationBank.credit(), destinationBank.id());
            mockApi.put(url, destinationBankUpdated);

            completed.complete(transaction);
        } else {
            faild.fail(transaction);
        }
    }
    public void setFaild(Transaction transaction){
        faild.fail(transaction);
    }
}
