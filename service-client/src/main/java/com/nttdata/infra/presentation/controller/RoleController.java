package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.client.SetClientActive;
import com.nttdata.application.usecase.role.FindRoleByClientId;
import com.nttdata.application.usecase.role.UpdateClientRole;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.exception.ClientNotExistsException;
import com.nttdata.infra.presentation.dtos.role.UpdateRoleDto;
import com.nttdata.infra.presentation.dtos.role.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final FindRoleByClientId findRoleByClientId;
    private final UpdateClientRole updateClientRole;
    private final SetClientActive setClientActive;

    public RoleController(FindRoleByClientId findRoleByClientId, UpdateClientRole updateClientRole, SetClientActive setClientActive) {
        this.findRoleByClientId = findRoleByClientId;
        this.updateClientRole = updateClientRole;
        this.setClientActive = setClientActive;
    }

    @GetMapping("/{id}")
    public ResponseEntity listarRoles(@PathVariable Long id) {
        try {
            Role role = findRoleByClientId.find(id);
            RoleDto roleDto = new RoleDto(role.getClientId(), role.getId(), role.getName());
            return ResponseEntity.ok(roleDto);
        }catch (ClientNotExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateClientRole(@RequestBody UpdateRoleDto dto) {
        try {
            var activateClient = updateClientRole.update(dto.clientId(), dto.roleName());
            setClientActive.set(dto.clientId(), true);
            return ResponseEntity.ok(activateClient);
        }catch (ClientNotExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
