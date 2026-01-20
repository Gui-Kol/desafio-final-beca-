package com.nttdata.infra.controller;

import com.nttdata.application.usecases.FindClientByCpf;
import com.nttdata.domain.client.Client;
import com.nttdata.infra.controller.dtos.ClientDto;
import com.nttdata.application.usecases.ListClients;
import com.nttdata.application.usecases.RegisterClientRoleClient;
import com.nttdata.infra.gateway.role.RoleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ListClients listClients;
    private final RegisterClientRoleClient registerClientRoleClient;
    private final RoleMapper roleMapper;
    private final FindClientByCpf findClientByCpf;

    public ClientController(ListClients listClients, RegisterClientRoleClient registerClientRoleClient, RoleMapper roleMapper, FindClientByCpf findClientByCpf) {
        this.listClients = listClients;
        this.registerClientRoleClient = registerClientRoleClient;
        this.roleMapper = roleMapper;
        this.findClientByCpf = findClientByCpf;
    }

    @GetMapping
    public ResponseEntity listClients() {
        var list = listClients.clientList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/role_client")
    public ResponseEntity<?> registerClient(@RequestBody ClientDto dto, UriComponentsBuilder builder) {
        String role = "ROLE_CLIENT";
        System.out.println(dto.roles());
        var clientToRegister = new Client(
                dto.id(),
                dto.name(),
                dto.email(),
                dto.address(),
                dto.username(),
                dto.password(),
                dto.cpf(),
                dto.birthDay(),
                dto.telephone(),
                LocalDate.now(),
                true
        );
        var registeredClient = registerClientRoleClient.register(clientToRegister);
        var uri = builder.path("/clients/{id}").buildAndExpand(registeredClient.getId()).toUri();
        return ResponseEntity.created(uri).body("Client registered successfully: " + registeredClient);
    }


}
