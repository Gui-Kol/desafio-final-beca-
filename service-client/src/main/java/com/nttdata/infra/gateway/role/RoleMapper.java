package com.nttdata.infra.gateway.role;


import com.nttdata.domain.role.Role;
import com.nttdata.infra.persistence.role.RoleEntity;

public class RoleMapper {
    public Role toRole(RoleEntity entities) {
        return new Role(entities.getClientId(),entities.getId(), entities.getName());
    }

    public RoleEntity toRoleEntity(Role roles) {
        return new RoleEntity(roles);
    }

}
