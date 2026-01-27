package com.nttdata.infra.gateway.transaction;

import com.nttdata.application.interfaces.TransactionRepository;
import com.nttdata.domain.transaction.Transaction;
import org.springframework.web.client.RestTemplate;

public class UpdateRepository implements TransactionRepository {
    private final RestTemplate restTemplate;

    public UpdateRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void update(Transaction transaction) {
        String url = "http://localhost:8082/update";
        restTemplate.put(url, transaction);
    }
}
