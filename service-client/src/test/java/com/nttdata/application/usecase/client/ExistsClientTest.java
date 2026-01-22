package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExistsClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ExistsClient existsClient;

    @Test
    @DisplayName("Deve retornar true se o cliente existir por ID")
    void shouldReturnTrueWhenClientExistsById() {
        Long clientId = 10L;
        when(clientRepository.existsClientByid(clientId)).thenReturn(true);

        boolean result = existsClient.byId(clientId);

        assertTrue(result);
        verify(clientRepository, times(1)).existsClientByid(clientId);
    }

    @Test
    @DisplayName("Deve retornar false se o cliente não existir por ID")
    void shouldReturnFalseWhenClientDoesNotExistById() {
        Long clientId = 20L;
        when(clientRepository.existsClientByid(clientId)).thenReturn(false);

        boolean result = existsClient.byId(clientId);

        assertFalse(result);
        verify(clientRepository, times(1)).existsClientByid(clientId);
    }
}