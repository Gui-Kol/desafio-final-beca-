package com.nttdata.infra.persitence.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepositoryEntity extends JpaRepository<TransactionEntity,Long> {

}
