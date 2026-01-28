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
class DebitCardTest {

    @Mock
    private PayBalanceInterface payBalance;

    @InjectMocks
    private DebitCard debitCard;

    @Test
    void payShouldCallPurchaseTransferWhenTypeIsPurchase() {
        Transaction transaction = new Transaction(
                1L,
                101L,
                202L,
                new BigDecimal("250.75"),
                "BRL",
                null,
                null,
                "Debit purchase",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.PURCHASE,
                PaymentMethod.DEBIT_CARD
        );

        debitCard.pay(transaction);

        verify(payBalance).purchaseTransfer(transaction);
        verify(payBalance, never()).setFaild(any(Transaction.class));
        verify(payBalance, never()).withdrawal(any(Transaction.class));
    }

    @Test
    void payShouldCallWithdrawalWhenTypeIsWithdrawal() {
        Transaction transaction = new Transaction(
                2L,
                103L,
                null,
                new BigDecimal("100.00"),
                "BRL",
                null,
                null,
                "Debit withdrawal",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.WITHDRAWAL,
                PaymentMethod.DEBIT_CARD
        );

        debitCard.pay(transaction);

        verify(payBalance).withdrawal(transaction);
        verify(payBalance, never()).setFaild(any(Transaction.class));
        verify(payBalance, never()).purchaseTransfer(any(Transaction.class));
    }

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"DEPOSIT", "TRANSFER"})
    void payShouldCallSetFaildForUnsupportedTypes(TransactionType type) {
        Transaction transaction = new Transaction(
                3L,
                104L,
                205L,
                new BigDecimal("50.00"),
                "USD",
                null,
                null,
                "Debit operation",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.DEBIT_CARD
        );

        debitCard.pay(transaction);

        verify(payBalance).setFaild(transaction);
        verify(payBalance, never()).purchaseTransfer(any(Transaction.class));
        verify(payBalance, never()).withdrawal(any(Transaction.class));
    }
}