package com.nttdata.domain.transaction;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionFactory {
    public Transaction factory(Long sourceAccountId, Long destinationAccountId, BigDecimal value,
                               String currency, String description, TransactionType type, PaymentMethod method){


        return new Transaction(null,sourceAccountId,destinationAccountId,value,currency,null,
                null,description, LocalDateTime.now(), StatusTransaction.PENDING,type,method);
    }


}
