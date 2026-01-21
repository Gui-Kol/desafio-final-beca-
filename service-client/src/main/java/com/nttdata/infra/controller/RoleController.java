package com.nttdata.infra.controller;

import com.nttdata.application.usecase.role.FindRoleByClientId;
import com.nttdata.application.usecase.role.UpdateClientRole;
import com.nttdata.domain.role.Role;
import com.nttdata.infra.controller.dtos.role.UpdateRoleDto;
import com.nttdata.infra.controller.dtos.role.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final FindRoleByClientId findRoleByClientId;
    private final UpdateClientRole updateClientRole;

    public RoleController(FindRoleByClientId findRoleByClientId, UpdateClientRole updateClientRole) {
        this.findRoleByClientId = findRoleByClientId;
        this.updateClientRole = updateClientRole;
    }

    @GetMapping("/{id}")
    public ResponseEntity listarRoles(@PathVariable Long id) {
        Role role = findRoleByClientId.find(id);
        RoleDto roleDto = new RoleDto(role.getClientId(), role.getId(), role.getName());
        return ResponseEntity.ok(roleDto);
    }

    @PutMapping
    public ResponseEntity updateClientRole(@RequestBody UpdateRoleDto dto) {
        System.out.println(dto.clientId() + dto.roleName().name());
        var activateClient = updateClientRole.update(dto.clientId(), dto.roleName());
        return ResponseEntity.ok(activateClient);
    }

}
