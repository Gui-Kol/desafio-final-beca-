package com.nttdata.infra.gateway.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecase.role.DeleteRoleClient;
import com.nttdata.application.usecase.role.RegisterRoleClient;
import com.nttdata.domain.client.Client;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.domain.exception.ClientInactiveException;
import com.nttdata.domain.exception.ClientNotExistsException;
import com.nttdata.domain.exception.RegistryClientException;
import com.nttdata.infra.persistence.client.ClientEntity;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class ClientRepositoryJpa implements ClientRepository {
    private final ClientRepositoryEntity entityRepository;
    private final ClientMapper clientMapper;
    private final RegisterRoleClient registerRoleClient;
    private final DeleteRoleClient deleteRoleClient;
    private final PasswordEncoder passwordEncoder;

    public ClientRepositoryJpa(ClientRepositoryEntity entityRepository, ClientMapper clientMapper, RegisterRoleClient registerRoleClient, DeleteRoleClient deleteRoleClient, PasswordEncoder passwordEncoder) {
        this.entityRepository = entityRepository;
        this.clientMapper = clientMapper;
        this.registerRoleClient = registerRoleClient;
        this.deleteRoleClient = deleteRoleClient;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<Client> clientList() {
        List<Client> list = entityRepository.findAll()
                .stream()
                .map(clientMapper::toClient)
                .toList();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ClientNotExistsException("There are no saved clients in the database!");
        }

    }

    @Override
    @Transactional
    public Client registerClientRoleClient(Client domainClient) {
        try {
            domainClient.setPassword(passwordEncoder.encode(domainClient.getPassword()));

            ClientEntity savedClientEntity = entityRepository.save(clientMapper.toEntity(domainClient));

            domainClient.setId(savedClientEntity.getId());

            Role domainRole = new Role(domainClient.getId(), RoleName.ROLE_CLIENT);
            registerRoleClient.register(domainRole);
            return domainClient;
        } catch (DataIntegrityViolationException e) {
            throw new RegistryClientException("A client with this information already exists.\n" + e.getMostSpecificCause());
        }

    }


    @Override
    public Client findClientByCpf(String cpf) {
        return clientMapper.toClient(entityRepository.findByCpf(cpf));
    }

    @Override
    public void deleteClient(Long id) {
        if (verifyActiveById(id)) {
            ClientEntity entityDeleted = entityRepository.getReferenceById(id);
            entityDeleted.setActive(false);
            deleteRoleClient.delete(id);
        } else {
            throw new ClientInactiveException("It is not possible to deactivate a client that is already inactive.");
        }
    }

    @Override
    @Transactional
    public Client updateClient(Client client, Long id) {
        try {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            ClientEntity oldEntity = entityRepository.getReferenceById(id);
            oldEntity.update(client);
            return clientMapper.toClient(entityRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new ClientNotExistsException("A client with ID " + id + " does not exist.");
        }
    }

    @Override
    public boolean existsClientByid(Long id) {

        if (entityRepository.existsById(id)) {
            return true;
        } else {
            throw new ClientNotExistsException("A client with ID " + id + " does not exist.");
        }
    }

    @Override
    public boolean verifyActiveById(Long id) {
        try {
            var entity = entityRepository.getReferenceById(id);
            return entity.isActive();
        } catch (EntityNotFoundException e) {
            throw new ClientNotExistsException("A client with ID " + id + " does not exist.");
        }
    }

    @Override
    public void setClientActive(Long id, boolean active) {
        try {
            var entity = entityRepository.getReferenceById(id);
            entity.setActive(active);
            entity.setLoginAttempts(0);
            entityRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ClientNotExistsException("A client with ID " + id + " does not exist.");
        }
    }

    @Override
    public int attemptsValidFail(String username) {
        var entity = entityRepository.getReferenceByUsername(username);
        entity.setLoginAttempts(entity.getLoginAttempts() + 1);
        entityRepository.save(entity);
        return entityRepository.getAttemptsByUsername(username);
    }

    @Override
    public void deleteClientByUsername(String username) {
        ClientEntity entityDeleted = entityRepository.getReferenceByUsername(username);
        entityDeleted.setActive(false);
        deleteRoleClient.delete(entityDeleted.getId());
        deleteRoleClient.delete(entityDeleted.getId());
    }

    @Override
    public int attemptsReset(String username) {
        var entity = entityRepository.getReferenceByUsername(username);
        entity.setLoginAttempts(0);
        entityRepository.save(entity);
        return entityRepository.getAttemptsByUsername(username);
    }
    @Override
    public boolean verifyActiveByUsername(String username) {
        try {
            var entity = entityRepository.getReferenceByUsername(username);
            return entity.isActive();
        } catch (EntityNotFoundException e) {
            throw new ClientNotExistsException("A client with username " + username + " does not exist.");
        }
    }

    @Override
    public int getAttemptsByUsername(String username) {
        return entityRepository.getAttemptsByUsername(username);
    }

}
