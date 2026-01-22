package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.client.SetClientActive;
import com.nttdata.application.usecase.role.FindRoleByClientId;
import com.nttdata.application.usecase.role.UpdateClientRole;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.infra.presentation.dtos.role.RoleDto;
import com.nttdata.infra.presentation.dtos.role.UpdateRoleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private FindRoleByClientId findRoleByClientId;
    @Mock
    private UpdateClientRole updateClientRole;
    @Mock
    private SetClientActive setClientActive;

    @InjectMocks
    private RoleController roleController;

    @Test
    void shouldReturnRoleDtoWhenRoleIsFound() {
        Long clientId = 1L;
        Role mockRole = new Role(1L, 10L, RoleName.ROLE_CLIENT);


        when(findRoleByClientId.find(clientId)).thenReturn(mockRole);

        ResponseEntity response = roleController.listarRoles(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new RoleDto(mockRole.getClientId(), mockRole.getId(), mockRole.getName()), response.getBody());
        verify(findRoleByClientId).find(clientId);
    }

    @Test
    void shouldReturnUpdatedRoleAndSetClientActive() {
        Long clientId = 1L;
        RoleName newRoleName = RoleName.ROLE_CLIENT;
        UpdateRoleDto updateRoleDto = new UpdateRoleDto(clientId, newRoleName);

        Role updatedRole = new Role(1L, 10L, RoleName.ROLE_CLIENT);


        when(updateClientRole.update(clientId, newRoleName)).thenReturn(updatedRole);

        ResponseEntity response = roleController.updateClientRole(updateRoleDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRole, response.getBody());
        verify(updateClientRole).update(clientId, newRoleName);
        verify(setClientActive).set(clientId, true);
    }
}