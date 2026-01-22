package com.nttdata.application.usecase.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindRoleByClientIdTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private FindRoleByClientId findRoleByClientId;

    @Test
    void shouldReturnRoleWhenRoleExistsForClientId() {
        Long clientId = 1L;
        Role expectedRole = new Role(1L,10L, RoleName.ROLE_CLIENT);

        when(repository.findRoleByClientId(clientId)).thenReturn(expectedRole);

        Role result = findRoleByClientId.find(clientId);

        assertEquals(expectedRole, result);
        verify(repository).findRoleByClientId(clientId);
    }

    @Test
    void shouldReturnNullWhenNoRoleExistsForClientId() {
        Long clientId = 2L;

        when(repository.findRoleByClientId(clientId)).thenReturn(null);

        Role result = findRoleByClientId.find(clientId);

        assertNull(result);
        verify(repository).findRoleByClientId(clientId);
    }
}