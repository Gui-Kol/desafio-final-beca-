package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyClientActiveTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private VerifyClientActive verifyClientActive;

    @Test
    void shouldReturnTrueWhenClientIsActive() {
        Long clientId = 1L;
        when(clientRepository.verifyActive(clientId)).thenReturn(true);

        boolean result = verifyClientActive.verifyById(clientId);

        assertTrue(result);
        verify(clientRepository).verifyActive(clientId);
    }

    @Test
    void shouldReturnFalseWhenClientIsInactive() {
        Long clientId = 2L;
        when(clientRepository.verifyActive(clientId)).thenReturn(false);

        boolean result = verifyClientActive.verifyById(clientId);

        assertFalse(result);
        verify(clientRepository).verifyActive(clientId);
    }
}