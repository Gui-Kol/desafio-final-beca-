package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayCreditInterface;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
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
class CreditCardTest {

    @Mock
    private PayCreditInterface payCredit;

    @InjectMocks
    private CreditCard creditCard;

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"DEPOSIT", "WITHDRAWAL"})
    void payShouldCallSetFaildForUnsupportedTypes(TransactionType type) {
        Transaction transaction = new Transaction(
                1L,
                100L,
                null,
                new BigDecimal("100.00"),
                "BRL",
                null,
                null,
                "Credit Card transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.CREDIT_CARD
        );

        creditCard.pay(transaction);

        verify(payCredit).setFaild(transaction);
        verify(payCredit, never()).purchaseTransfer(any(Transaction.class));
    }

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"PURCHASE", "TRANSFER"})
    void payShouldCallPurchaseTransferForSupportedTypes(TransactionType type) {
        Transaction transaction = new Transaction(
                2L,
                101L,
                202L,
                new BigDecimal("500.00"),
                "USD",
                null,
                null,
                "Credit Card transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.CREDIT_CARD
        );

        creditCard.pay(transaction);

        verify(payCredit).purchaseTransfer(transaction);
        verify(payCredit, never()).setFaild(any(Transaction.class));
    }
}