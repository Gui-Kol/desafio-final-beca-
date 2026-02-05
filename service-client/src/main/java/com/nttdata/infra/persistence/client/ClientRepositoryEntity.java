package com.nttdata.infra.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientRepositoryEntity extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByCpf(String cpf);

    UserDetails findByUsername(String username);


    @Query("SELECT c.loginAttempts FROM client c WHERE c.username = :username")
    Integer getAttemptsByUsername(@Param("username") String username);

    ClientEntity getReferenceByUsername(String username);
}