package com.nttdata.infra.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientRepositoryEntity extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByCpf(String cpf);

    UserDetails findByUsername(String username);
}