package com.nttdata.infra.controller.dtos.role;

import com.nttdata.domain.role.RoleName;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleDto(
        @NotNull
        Long clientId,
        @NotNull
        RoleName roleName
) {


}

