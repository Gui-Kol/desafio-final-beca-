package com.nttdata.infra.presentation.dto.transaction;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionDto(
        @NotBlank
        Long sourceAccountId,
        @NotBlank
        Long destinationAccountId,
        @NotBlank
        @Positive
        BigDecimal value,
        @NotBlank
        String currency,
        @NotBlank
        String description,
        @NotBlank
        TransactionType type,
        @NotBlank
        PaymentMethod method
) {
}
