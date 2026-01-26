package com.nttdata.infra.gateway.processor;

import com.nttdata.application.interfaces.PayCreditInterface;
import com.nttdata.domain.bank.Bank;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.bank.BankDto;
import com.nttdata.application.usecases.processor.Completed;
import com.nttdata.application.usecases.processor.Faild;
import com.nttdata.infra.service.MockApiService;

public class PayCredit implements PayCreditInterface {
    private final Faild faild;
    private final Completed completed;
    private final MockApiService mockApiService;


    String url = "http://6973d075b5f46f8b5828534e.mockapi.io/mockapi/bank/";

    public PayCredit(Faild faild, Completed completed, MockApiService mockApiService) {
        this.faild = faild;
        this.completed = completed;
        this.mockApiService = mockApiService;
    }


    public void purchaseTransfer(Transaction transaction) {
        BankDto sourceBank = mockApiService.get(transaction.getSourceAccountId());
        BankDto destinationBank = mockApiService.get(transaction.getDestinationAccountId());
        var transactionValue = transaction.getValue().doubleValue();

        if (sourceBank.credit() >= transactionValue) {
            var remainingSource = sourceBank.credit() - transactionValue;
            var remainingDestination = destinationBank.balance() + transactionValue;

            Bank sourceBankUpdated = new Bank(sourceBank.account(), sourceBank.clientId(), sourceBank.balance(), remainingSource, sourceBank.id());
            mockApiService.put(url, sourceBankUpdated);

            Bank destinationBankUpdated = new Bank(destinationBank.account(), destinationBank.clientId(), remainingDestination, destinationBank.credit(), destinationBank.id());
            mockApiService.put(url, destinationBankUpdated);

            completed.complete(transaction);
        } else {
            faild.fail(transaction);
        }
    }
    public void setFaild(Transaction transaction){
        faild.fail(transaction);
    }
}
