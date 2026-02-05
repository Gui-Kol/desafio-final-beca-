package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;

public class VerifyClientActive {
    private final ClientRepository clientRepository;

    public VerifyClientActive(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public boolean verifyById(Long id){
        return clientRepository.verifyActiveById(id);
    }

    public boolean verifyByUsername(String username) {
        return clientRepository.verifyActiveByUsername(username);
    }
}
