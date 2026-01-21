package com.nttdata.configs;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.application.usecases.role.DeleteRoleClient;
import com.nttdata.application.usecases.role.FindRoleByClientId;
import com.nttdata.application.usecases.role.RegisterRoleClient;
import com.nttdata.application.usecases.role.UpdateClientRole;
import com.nttdata.infra.gateway.role.RoleMapper;
import com.nttdata.infra.gateway.role.RoleRepositoryJpa;
import com.nttdata.infra.persistence.role.RoleRepositoryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {
    @Bean
    public RoleMapper roleMapper(){
        return new RoleMapper();
    }
    @Bean
    public RegisterRoleClient registerRoleClient(RoleRepository repository){
        return new RegisterRoleClient(repository);
    }
    @Bean
    public RoleRepositoryJpa repositoryJpa(RoleRepositoryEntity repositoryEntity, RoleMapper roleMapper){
        return new RoleRepositoryJpa(repositoryEntity, roleMapper());
    }
    @Bean
    public DeleteRoleClient deleteRoleClient(RoleRepository repository){
        return new DeleteRoleClient(repository);
    }
    @Bean
    public FindRoleByClientId findRoleByClientId(RoleRepository repository){
        return new FindRoleByClientId(repository);
    }
    @Bean
    public UpdateClientRole updateClientRole(RoleRepository repository){
        return new UpdateClientRole(repository);
    }
}
