package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayBalanceInterface;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CashTest {

    @Mock
    private PayBalanceInterface payBalance;

    @InjectMocks
    private Cash cash;

    @Test
    void payShouldCallDepositWhenTypeIsDeposit() {
        Transaction transaction = new Transaction(
                1L,
                null,
                101L,
                new BigDecimal("100.00"),
                "BRL",
                null,
                null,
                "Cash deposit",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.DEPOSIT,
                PaymentMethod.CASH
        );

        cash.pay(transaction);

        verify(payBalance).deposit(transaction);
        verify(payBalance, never()).setCompleted(any(Transaction.class));
        verify(payBalance, never()).withdrawal(any(Transaction.class));
    }

    @Test
    void payShouldCallWithdrawalWhenTypeIsWithdrawal() {
        Transaction transaction = new Transaction(
                2L,
                102L,
                null,
                new BigDecimal("50.00"),
                "BRL",
                null,
                null,
                "Cash withdrawal",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.WITHDRAWAL,
                PaymentMethod.CASH
        );

        cash.pay(transaction);

        verify(payBalance).withdrawal(transaction);
        verify(payBalance, never()).setCompleted(any(Transaction.class));
        verify(payBalance, never()).deposit(any(Transaction.class));
    }

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"PURCHASE", "TRANSFER"})
    void payShouldCallSetCompletedForOtherTypes(TransactionType type) {
        Transaction transaction = new Transaction(
                3L,
                null,
                null,
                new BigDecimal("25.00"),
                "BRL",
                null,
                null,
                "Cash transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.CASH
        );

        cash.pay(transaction);

        verify(payBalance).setCompleted(transaction);
        verify(payBalance, never()).deposit(any(Transaction.class));
        verify(payBalance, never()).withdrawal(any(Transaction.class));
    }
}