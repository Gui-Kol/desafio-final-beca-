package com.nttdata.infra.gateway.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecase.role.DeleteRoleClient;
import com.nttdata.application.usecase.role.RegisterRoleClient;
import com.nttdata.domain.client.Client;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.infra.persistence.client.ClientEntity;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import jakarta.transaction.Transactional;
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
        return entityRepository.findAll()
                .stream()
                .map(clientMapper::toClient)
                .toList();
    }

    @Override
    @Transactional
    public Client registerClientRoleClient(Client domainClient) {
        domainClient.setPassword(passwordEncoder.encode(domainClient.getPassword()));

        ClientEntity savedClientEntity = entityRepository.save(clientMapper.toEntity(domainClient));

        domainClient.setId(savedClientEntity.getId());

        Role domainRole = new Role(domainClient.getId(), RoleName.ROLE_CLIENT);
        registerRoleClient.register(domainRole);
        return domainClient;
    }


    @Override
    public Client findClientByCpf(String cpf) {
        return clientMapper.toClient(entityRepository.findByCpf(cpf));
    }

    @Override
    public void deleteClient(Long id) {
        ClientEntity entityDeleted = entityRepository.getReferenceById(id);
        entityDeleted.setActive(false);
        deleteRoleClient.delete(id);
    }

    @Override
    @Transactional
    public Client updateClient(Client client, Long id) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        ClientEntity oldEntity = entityRepository.getReferenceById(id);
        oldEntity.update(client);
        return clientMapper.toClient(entityRepository.getReferenceById(id));
    }

    @Override
    public boolean existsClientByid(Long id) {
        return entityRepository.existsById(id);
    }

    @Override
    public boolean verifyActive(Long id) {
        try {
            var entity = entityRepository.getReferenceById(id);
            return entity.isActive();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void setClientActive(Long id, boolean active) {
        var entity = entityRepository.getReferenceById(id);
        entity.setActive(active);
        entityRepository.save(entity);
    }
}
