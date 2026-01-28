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
class PixTest {

    @Mock
    private PayBalanceInterface payBalance;

    @InjectMocks
    private Pix pix;

    @Test
    void payShouldCallSetFaildWhenTypeIsDeposit() {
        Transaction transaction = new Transaction(
                1L,
                null,
                101L,
                new BigDecimal("100.00"),
                "BRL",
                null,
                null,
                "Pix deposit attempt",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.DEPOSIT,
                PaymentMethod.PIX
        );

        pix.pay(transaction);

        verify(payBalance).setFaild(transaction);
        verify(payBalance, never()).purchaseTransfer(any(Transaction.class));
        verify(payBalance, never()).withdrawal(any(Transaction.class));
    }

    @Test
    void payShouldCallWithdrawalWhenTypeIsWithdrawal() {
        Transaction transaction = new Transaction(
                2L,
                102L,
                null,
                new BigDecimal("200.00"),
                "BRL",
                null,
                null,
                "Pix withdrawal",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.WITHDRAWAL,
                PaymentMethod.PIX
        );

        pix.pay(transaction);

        verify(payBalance).withdrawal(transaction);
        verify(payBalance, never()).setFaild(any(Transaction.class));
        verify(payBalance, never()).purchaseTransfer(any(Transaction.class));
    }

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"PURCHASE", "TRANSFER"})
    void payShouldCallPurchaseTransferForSupportedTypes(TransactionType type) {
        Transaction transaction = new Transaction(
                3L,
                103L,
                203L,
                new BigDecimal("350.50"),
                "BRL",
                null,
                null,
                "Pix transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.PIX
        );

        pix.pay(transaction);

        verify(payBalance).purchaseTransfer(transaction);
        verify(payBalance, never()).setFaild(any(Transaction.class));
        verify(payBalance, never()).withdrawal(any(Transaction.class));
    }
}