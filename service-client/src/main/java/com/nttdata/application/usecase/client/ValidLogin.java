package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.exception.ValidException;

public class ValidLogin {
    int maxAttempts = 5;

    private final ClientRepository repository;

    public ValidLogin(ClientRepository repository) {
        this.repository = repository;
    }


    public void registerFailedAttempt(String username) {
        int attempts = repository.attemptsValidFail(username);
        if (attempts < maxAttempts) {
            var response = maxAttempts - attempts;
            throw new ValidException("You have " + response + " more attempts");
        }else {
            repository.deleteClientByUsername(username);
        }
    }

    public void resetAttempts(String username) {
        repository.attemptsReset(username);
    }

    public void checkIfClientIsBlocked(String username) {
        int attempts = repository.getAttemptsByUsername(username);
        if (attempts >= maxAttempts){
            throw new ValidException("Client blocked due to too many attempts!");
        }
    }
}
