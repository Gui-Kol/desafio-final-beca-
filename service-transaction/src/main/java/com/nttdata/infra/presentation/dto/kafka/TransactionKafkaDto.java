package com.nttdata.infra.presentation.dto.kafka;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

import java.math.BigDecimal;

public record TransactionKafkaDto(
        Long id,
        Long sourceAccountId,
        Long destinationAccountId,
        BigDecimal value,
        String currency,
        BigDecimal appliedExchangeRate,
        BigDecimal convertedValue,
        String description,
        String transactionDate,
        StatusTransaction status,
        TransactionType type,
        PaymentMethod method
){
}
