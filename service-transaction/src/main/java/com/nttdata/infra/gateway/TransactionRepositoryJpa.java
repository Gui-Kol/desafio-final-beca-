package com.nttdata.infra.gateway;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.infra.service.ClientValidationService;

public class TransactionRepositoryJpa implements TransactionRepository {
    private final ClientValidationService clientValidationService;

    public TransactionRepositoryJpa(ClientValidationService clientValidationService) {
        this.clientValidationService = clientValidationService;
    }


    @Override
    public boolean validClient(Long sourceAccountId) {
        boolean response = clientValidationService.checkClientExistence(sourceAccountId);
        System.out.println(response);
        return response;
    }
}
