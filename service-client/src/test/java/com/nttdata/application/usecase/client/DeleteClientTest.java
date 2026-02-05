package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.exception.ClientInactiveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private DeleteClient deleteClient;

    @Test
    @DisplayName("Deve deletar o cliente se ele estiver ativo")
    void shouldDeleteClientWhenActive() {
        Long clientId = 123L;

        when(clientRepository.verifyActiveById(clientId)).thenReturn(true);

        deleteClient.deleteClient(clientId);

        verify(clientRepository, times(1)).verifyActiveById(clientId);
        verify(clientRepository, times(1)).deleteClient(clientId);
    }

    @Test
    @DisplayName("Deve lançar ClientInactiveException se o cliente estiver inativo")
    void shouldThrowClientInactiveExceptionWhenInactive() {
        Long clientId = 456L;

        when(clientRepository.verifyActiveById(clientId)).thenReturn(false);

        ClientInactiveException thrown = assertThrows(ClientInactiveException.class, () -> {
            deleteClient.deleteClient(clientId);
        });

        assertEquals("Client already inactive!", thrown.getMessage());
        verify(clientRepository, times(1)).verifyActiveById(clientId);
        verify(clientRepository, never()).deleteClient(clientId);
    }

    @Test
    @DisplayName("Deve lidar com múltiplas chamadas, deletando apenas clientes ativos")
    void shouldHandleMultipleCallsDeletingOnlyActiveClients() {
        Long activeClientId = 101L;
        Long inactiveClientId = 102L;

        when(clientRepository.verifyActiveById(activeClientId)).thenReturn(true);
        when(clientRepository.verifyActiveById(inactiveClientId)).thenReturn(false);

        deleteClient.deleteClient(activeClientId);

        assertThrows(ClientInactiveException.class, () -> {
            deleteClient.deleteClient(inactiveClientId);
        });
        deleteClient.deleteClient(activeClientId);
        verify(clientRepository, times(2)).verifyActiveById(activeClientId);
        verify(clientRepository, times(1)).verifyActiveById(inactiveClientId);
        verify(clientRepository, times(2)).deleteClient(activeClientId);
        verify(clientRepository, never()).deleteClient(inactiveClientId);
    }
}