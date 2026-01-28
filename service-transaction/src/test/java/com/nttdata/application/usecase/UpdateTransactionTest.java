package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UpdateTransactionTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private UpdateTransaction updateTransaction;

    @Test
    void shouldCallRepositoryToUpdateTransaction() {
        Transaction transactionToUpdate = new Transaction(1L, 100L, 101L, null, null, null, null, null, null, null, null, null);

        updateTransaction.update(transactionToUpdate);

        verify(repository).update(transactionToUpdate);
        verifyNoMoreInteractions(repository);
    }
}