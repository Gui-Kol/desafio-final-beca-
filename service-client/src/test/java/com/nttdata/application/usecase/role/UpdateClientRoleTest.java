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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateClientRoleTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private UpdateClientRole updateClientRole;

    @Test
    void shouldUpdateClientRoleSuccessfully() {
        Long clientId = 1L;
        RoleName newRoleName = RoleName.ROLE_CLIENT;

        Role expectedUpdatedRole = new Role(1L,10L, RoleName.ROLE_CLIENT);

        when(repository.updateClientRole(clientId, newRoleName)).thenReturn(expectedUpdatedRole);

        Role result = updateClientRole.update(clientId, newRoleName);

        assertEquals(expectedUpdatedRole, result);
        verify(repository).updateClientRole(clientId, newRoleName);
    }
}