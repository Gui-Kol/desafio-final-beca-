package com.nttdata.infra.gateway;

import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.persistence.client.TransactionEntity;

public class TransactionMapper {

    public Transaction toTransaction(TransactionEntity entity){
        return new Transaction(entity.getId(),entity.getSourceAccountId(),entity.getDestinationAccountId(),entity.getValue(),entity.getCurrency(),
                entity.getAppliedExchangeRate(),entity.getConvertedValue(),entity.getDescription(),entity.getTransactionDate(),entity.getStatus(),
                entity.getType(),entity.getMethod());
    }
    public TransactionEntity toTransactionEntity(Transaction transaction){
        return new TransactionEntity(transaction.getId(),transaction.getSourceAccountId(),transaction.getDestinationAccountId(),transaction.getValue(),
                transaction.getCurrency(),transaction.getAppliedExchangeRate(),transaction.getConvertedValue(),transaction.getDescription(),
                transaction.getTransactionDate(),transaction.getStatus(),transaction.getType(),transaction.getMethod());
    }

}
