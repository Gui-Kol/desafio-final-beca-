package com.nttdata.infra.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepositoryEntity extends JpaRepository<TransactionEntity,Long> {

    List<TransactionEntity> findBySourceAccountId(Long clientId);

    boolean existsBySourceAccountId(Long clientId);

    List<TransactionEntity> findByDestinationAccountId(Long clientId);

    boolean existsByDestinationAccountId(Long clientId);
}
