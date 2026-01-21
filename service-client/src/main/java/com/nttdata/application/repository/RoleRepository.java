package com.nttdata.application.repository;

import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;

public interface RoleRepository {
    Role registerRoleClient(Role role);

    void deleteRoleClient(Long id);

    Role findRoleByClientId(Long id);

    Role updateClientRole(Long clientId, RoleName roleName);
}
