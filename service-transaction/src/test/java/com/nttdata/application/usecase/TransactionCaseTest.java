package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.exception.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionCaseTest {

    @Mock
    private TransactionRepository repository;

    private TransactionCase transactionCase;

    @BeforeEach
    void setUp() {
        transactionCase = new TransactionCase(repository);
    }

    @Test
    void shouldSavePendingTransactionWhenBothClientsAreValid() {
        Transaction inputTransaction = new Transaction(null, 100L, 101L, null, null, null, null, null, null, null, null, null);
        Transaction expectedSavedTransaction = new Transaction(1L, 100L, 101L, null, null, null, null, null, null, null, null, null);

        when(repository.validClient(100L)).thenReturn(true);
        when(repository.validClient(101L)).thenReturn(true);
        when(repository.saveTransactionPending(inputTransaction)).thenReturn(expectedSavedTransaction);

        Transaction actualTransaction = transactionCase.transaction(inputTransaction);

        assertNotNull(actualTransaction);
        assertEquals(expectedSavedTransaction, actualTransaction);

        verify(repository).validClient(100L);
        verify(repository).validClient(101L);
        verify(repository).saveTransactionPending(inputTransaction);
    }

    @Test
    void shouldThrowTransactionExceptionWhenSourceClientIsInvalid() {
        Transaction inputTransaction = new Transaction(null, 300L, 301L, null, null, null, null, null, null, null, null, null);

        when(repository.validClient(300L)).thenReturn(false);
        when(repository.validClient(301L)).thenReturn(true);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionCase.transaction(inputTransaction);
        });

        assertEquals("The client is invalid or inactive!", exception.getMessage());

        verify(repository).validClient(300L);
        verify(repository).validClient(301L);
        verify(repository, never()).saveTransactionPending(any(Transaction.class));
    }

    @Test
    void shouldThrowTransactionExceptionWhenDestinationClientIsInvalid() {
        Transaction inputTransaction = new Transaction(null, 300L, 301L, null, null, null, null, null, null, null, null, null);

        when(repository.validClient(300L)).thenReturn(true);
        when(repository.validClient(301L)).thenReturn(false);

        TransactionException exception = assertThrows(TransactionException.class, () -> {
            transactionCase.transaction(inputTransaction);
        });

        assertEquals("The client is invalid or inactive!", exception.getMessage());

        verify(repository).validClient(300L);
        verify(repository).validClient(301L);
        verify(repository, never()).saveTransactionPending(any(Transaction.class));
    }
}