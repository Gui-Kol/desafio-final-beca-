package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionCaseTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionCase transactionCase;

    @Test
    void transaction() {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 2L;
        Transaction transaction = new Transaction(1L, sourceAccountId, destinationAccountId, new BigDecimal("100.00"), "BRL", null, null, "Test transfer", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.DEBIT_CARD);

        doAnswer(invocation -> null).when(repository).validClient(sourceAccountId);
        doAnswer(invocation -> null).when(repository).validClient(destinationAccountId);
        when(repository.saveTransactionPending(transaction)).thenReturn(transaction);

        Transaction result = transactionCase.transaction(transaction);

        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
        assertEquals(sourceAccountId, result.getSourceAccountId());
        assertEquals(destinationAccountId, result.getDestinationAccountId());

        verify(repository, times(1)).validClient(sourceAccountId);
        verify(repository, times(1)).validClient(destinationAccountId);
        verify(repository, times(1)).saveTransactionPending(transaction);
        verify(repository, never()).saveTransactionCompleted(any(Transaction.class));
    }

    @Test
    void transactionExternal() {
        Long sourceAccountId = 3L;
        Transaction transaction = new Transaction(2L, sourceAccountId, null, new BigDecimal("250.50"), "USD", null, null, "External test", LocalDateTime.now(), StatusTransaction.COMPLETED, TransactionType.PURCHASE, PaymentMethod.CREDIT_CARD);

        doAnswer(invocation -> null).when(repository).validClient(sourceAccountId);
        when(repository.saveTransactionCompleted(transaction)).thenReturn(transaction);

        Transaction result = transactionCase.transactionExternal(transaction);

        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
        assertEquals(sourceAccountId, result.getSourceAccountId());

        verify(repository, times(1)).validClient(sourceAccountId);
        verify(repository, times(1)).saveTransactionCompleted(transaction);
        verify(repository, never()).saveTransactionPending(any(Transaction.class));
    }
}