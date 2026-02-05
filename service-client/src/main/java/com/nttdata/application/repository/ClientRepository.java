package com.nttdata.application.repository;

import com.nttdata.domain.client.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> clientList();

    Client registerClientRoleClient(Client client);

    Client findClientByCpf(String cpf);

    void deleteClient(Long id);

    Client updateClient(Client client, Long id);

    boolean existsClientByid(Long id);

    boolean verifyActiveById(Long id);

    void setClientActive(Long id, boolean active);

    int attemptsValidFail(String username);

    void deleteClientByUsername(String username);

    int attemptsReset(String username);

    boolean verifyActiveByUsername(String username);

    int getAttemptsByUsername(String username);
}
