package com.nttdata.application.repository;

public interface TransactionRepository {
    boolean validClient(Long sourceAccountId);
}
