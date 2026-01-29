package com.nttdata.infra.presentation.dto.transaction;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionDto(
        @NotNull(message = "The value cannot be null.")
        Long sourceAccountId,
        @NotNull(message = "The value cannot be null.")
        Long destinationAccountId,
        @NotNull(message = "The value cannot be null.")
        @Positive(message = "The value must be greater than 0.")
        BigDecimal value,
        @NotNull(message = "The value cannot be null.")
        String currency,
        @NotBlank(message = "The description cannot be null.")
        String description,
        @NotNull(message = "The type cannot be null.")
        TransactionType type,
        @NotNull(message = "The Method cannot be null.")
        PaymentMethod method
) {
}
