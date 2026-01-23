package com.nttdata.infra.presentation.dto.kafka;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

public record CancelTransactionKafkaDto(
        Long id,
        Long sourceAccountId,
        Long destinationAccountId,
        StatusTransaction status,
        TransactionType type,
        PaymentMethod method
) {
}
