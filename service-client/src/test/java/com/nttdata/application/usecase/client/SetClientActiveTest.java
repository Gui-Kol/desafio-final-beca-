package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SetClientActiveTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private SetClientActive setClientActive;

    @Test
    void shouldCallSetClientActiveWithCorrectParameters() {
        Long clientId = 1L;
        boolean activeStatus = true;

        setClientActive.set(clientId, activeStatus);

        verify(repository).setClientActive(clientId, activeStatus);
    }

    @Test
    void shouldCallSetClientInactiveWithCorrectParameters() {
        Long clientId = 2L;
        boolean activeStatus = false;

        setClientActive.set(clientId, activeStatus);

        verify(repository).setClientActive(clientId, activeStatus);
    }
}