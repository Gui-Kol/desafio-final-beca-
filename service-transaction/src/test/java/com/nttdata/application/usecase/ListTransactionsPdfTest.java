package com.nttdata.application.usecase;

import com.nttdata.application.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ListTransactionsPdfTest {

    @Mock
    private TransactionRepository repository;

    private ListTransactionsPdf listTransactionsPdf;

    @BeforeEach
    void setUp() {
        listTransactionsPdf = new ListTransactionsPdf(repository);
    }

    @Test
    void shouldCallRepositoryToCreatePdf() {
        Long clientId = 1L;
        int day = 1;

        listTransactionsPdf.create(clientId, day);

        verify(repository).createPdf(clientId, day);
        verifyNoMoreInteractions(repository);
    }
}