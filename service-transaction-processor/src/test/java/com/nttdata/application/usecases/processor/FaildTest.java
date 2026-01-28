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
class FaildTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private Faild faild;

    @Test
    void failShouldSetStatusToFailedAndUpdateRepository() {
        Transaction transaction = new Transaction(
                1L,
                101L,
                202L,
                new BigDecimal("250.00"),
                "BRL",
                null,
                null,
                "Initial transaction",
                LocalDateTime.now(),
                StatusTransaction.PENDING,
                TransactionType.TRANSFER,
                PaymentMethod.PIX
        );

        faild.fail(transaction);

        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository, times(1)).update(transactionCaptor.capture());

        Transaction capturedTransaction = transactionCaptor.getValue();

        assertEquals(StatusTransaction.FAILED, capturedTransaction.getStatus());
        assertEquals(transaction.getId(), capturedTransaction.getId());
    }
}