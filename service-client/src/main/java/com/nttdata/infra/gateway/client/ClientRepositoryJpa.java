package com.nttdata.infra.gateway.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecases.role.DeleteRoleClient;
import com.nttdata.application.usecases.role.RegisterRoleClient;
import com.nttdata.domain.client.Client;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.infra.gateway.role.RoleMapper;
import com.nttdata.infra.gateway.role.RoleRepositoryJpa;
import com.nttdata.infra.persistence.client.ClientEntity;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public class ClientRepositoryJpa implements ClientRepository {
    private final ClientRepositoryEntity entityRepository;
    private final ClientMapper clientMapper;
    private final RegisterRoleClient registerRoleClient;
    private final DeleteRoleClient deleteRoleClient;
    private final RoleRepositoryJpa roleRepositoryJpa;
    private final RoleMapper roleMapper;

    public ClientRepositoryJpa(ClientRepositoryEntity entityRepository, ClientMapper mapper, RegisterRoleClient registerRoleClient, DeleteRoleClient deleteRoleClient, RoleRepositoryJpa roleRepositoryJpa, ClientMapper clientMapper, RoleMapper roleMapper) {
        this.entityRepository = entityRepository;
        this.registerRoleClient = registerRoleClient;
        this.deleteRoleClient = deleteRoleClient;
        this.roleRepositoryJpa = roleRepositoryJpa;
        this.clientMapper = clientMapper;
        this.roleMapper = roleMapper;
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
        System.out.println("Client de domínio recebido para persistência: " + domainClient);

        ClientEntity savedClientEntity = entityRepository.save(clientMapper.toEntity(domainClient));

        domainClient.setId(savedClientEntity.getId());
        System.out.println("Client de domínio após salvar e obter ID: " + domainClient);
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
        ClientEntity entityDeleted = entityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + id + " não encontrado para exclusão."));
        entityRepository.delete(entityDeleted);
        deleteRoleClient.delete(id);
    }
}
