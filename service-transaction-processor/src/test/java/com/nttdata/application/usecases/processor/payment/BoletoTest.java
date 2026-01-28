package com.nttdata.application.usecases.processor.payment;

import com.nttdata.application.interfaces.PayBalanceInterface;
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
class BoletoTest {

    @Mock
    private PayBalanceInterface payBalance;

    @InjectMocks
    private Boleto boleto;

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
                "Boleto transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.BOLETO
        );

        boleto.pay(transaction);

        verify(payBalance).setFaild(transaction);
        verify(payBalance, never()).purchaseTransfer(any(Transaction.class));
    }

    @ParameterizedTest
    @EnumSource(value = TransactionType.class, names = {"PURCHASE", "TRANSFER"})
    void payShouldCallPurchaseTransferForSupportedTypes(TransactionType type) {
        Transaction transaction = new Transaction(
                2L,
                101L,
                202L,
                new BigDecimal("200.00"),
                "BRL",
                null,
                null,
                "Boleto transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                type,
                PaymentMethod.BOLETO
        );

        boleto.pay(transaction);

        verify(payBalance).purchaseTransfer(transaction);
        verify(payBalance, never()).setFaild(any(Transaction.class));
    }
}