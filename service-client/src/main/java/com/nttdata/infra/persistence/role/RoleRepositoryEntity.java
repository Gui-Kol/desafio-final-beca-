package com.nttdata.infra.persistence.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositoryEntity extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByClientId(Long id);
}
