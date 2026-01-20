package com.nttdata.infra.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositoryEntity extends JpaRepository<ClientEntity, Long> {
}