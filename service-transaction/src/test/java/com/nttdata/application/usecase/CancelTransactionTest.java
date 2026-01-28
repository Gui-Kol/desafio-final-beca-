package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelTransactionTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CancelTransaction cancelTransaction;

    @Test
    void shouldCallRepositoryAndReturnTransactionWhenCancelIsCalled() {
        Long transactionId = 123L;
        Transaction expectedTransaction = new Transaction(transactionId, null, null, null, null,
                null, null, null, null, null, null, null);

        when(transactionRepository.cancelTransaction(transactionId)).thenReturn(expectedTransaction);

        Transaction actualTransaction = cancelTransaction.cancel(transactionId);

        verify(transactionRepository, times(1)).cancelTransaction(transactionId);

        verifyNoMoreInteractions(transactionRepository);

        assertNotNull(actualTransaction);
        assertEquals(expectedTransaction, actualTransaction);
        assertEquals(transactionId, actualTransaction.getId());
    }
}