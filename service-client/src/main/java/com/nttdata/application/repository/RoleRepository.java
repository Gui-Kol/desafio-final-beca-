package com.nttdata.application.repository;

import com.nttdata.domain.role.Role;

public interface RoleRepository {
    Role registerRoleClient(Role role);

    void deleteRoleClient(Long id);
}
