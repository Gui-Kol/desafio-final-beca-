package com.nttdata.infra.presentation.dtos.role;

import com.nttdata.domain.role.RoleName;

public record RoleDto(
        long id,
        long clientId,
        RoleName name

) {
}
