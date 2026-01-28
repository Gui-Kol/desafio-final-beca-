package com.nttdata.application.usecases.processor;

import com.nttdata.application.interfaces.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompletedTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private Completed completed;

    @Test
    void completeShouldSetStatusToCompletedAndUpdateRepository() {
        Transaction transaction = new Transaction(
                1L,
                101L,
                202L,
                new BigDecimal("500.00"),
                "USD",
                new BigDecimal("5.0"),
                new BigDecimal("2500.00"),
                "Test transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.PURCHASE,
                PaymentMethod.DEBIT_CARD
        );

        completed.complete(transaction);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).update(transactionCaptor.capture());

        Transaction capturedTransaction = transactionCaptor.getValue();

        assertEquals(StatusTransaction.COMPLETED, capturedTransaction.getStatus());
        assertEquals(transaction.getId(), capturedTransaction.getId());
    }
}