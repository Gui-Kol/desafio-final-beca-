package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.UpdateTransaction;
import com.nttdata.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTransactionControllerTest {

    @Mock
    private UpdateTransaction updateTransaction;

    @InjectMocks
    private UpdateTransactionController updateTransactionController;

    @Test
    void shouldCallUpdateUseCase() {
        Transaction inputTransaction = new Transaction(1L, 100L, 101L, null, null, null, null, null, null, null, null, null);

        doNothing().when(updateTransaction).update(inputTransaction);

        updateTransactionController.updateTransaction(inputTransaction);

        verify(updateTransaction, times(1)).update(inputTransaction);
        verifyNoMoreInteractions(updateTransaction);
    }
}