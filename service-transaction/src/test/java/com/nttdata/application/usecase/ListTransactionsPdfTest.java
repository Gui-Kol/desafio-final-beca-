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

        listTransactionsPdf.create(clientId);

        verify(repository).createPdf(clientId);
        verifyNoMoreInteractions(repository);
    }
}