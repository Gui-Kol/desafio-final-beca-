package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTransactionsTest {

    @Mock
    private TransactionRepository repository;

    private ListTransactions listTransactions;

    @BeforeEach
    void setUp() {
        listTransactions = new ListTransactions(repository);
    }

    @Test
    void shouldListTransactionsByClientId() {
        Long clientId = 1L;
        List<Transaction> expectedTransactions = List.of(
                new Transaction(1L, null, null, null, null, null, null, null, null, null, null, null),
                new Transaction(2L, null, null, null, null, null, null, null, null, null, null, null)
        );

        when(repository.listByClientId(clientId)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = listTransactions.byClientId(clientId);

        assertNotNull(actualTransactions);
        assertEquals(2, actualTransactions.size());
        assertEquals(expectedTransactions, actualTransactions);
        verify(repository).listByClientId(clientId);
    }
}