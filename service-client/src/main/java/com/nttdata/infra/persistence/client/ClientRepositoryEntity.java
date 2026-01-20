package com.nttdata.infra.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepositoryEntity extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByCpf(String cpf);
}