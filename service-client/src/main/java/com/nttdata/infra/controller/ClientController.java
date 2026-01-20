package com.nttdata.infra.controller;

import com.nttdata.application.usecases.client.DeleteClient;
import com.nttdata.application.usecases.client.FindClientByCpf;
import com.nttdata.application.usecases.client.ListClients;
import com.nttdata.application.usecases.client.RegisterClientRoleClient;
import com.nttdata.domain.client.Client;
import com.nttdata.infra.controller.dtos.ClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ListClients listClients;
    private final RegisterClientRoleClient registerClientRoleClient;
    private final DeleteClient deleteClient;

    private final FindClientByCpf findClientByCpf;

    public ClientController(ListClients listClients, RegisterClientRoleClient registerClientRoleClient, DeleteClient deleteClient, FindClientByCpf findClientByCpf) {
        this.listClients = listClients;
        this.registerClientRoleClient = registerClientRoleClient;
        this.deleteClient = deleteClient;
        this.findClientByCpf = findClientByCpf;
    }

    @GetMapping
    public ResponseEntity listClients() {
        var list = listClients.clientList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/role_client")
    public ResponseEntity<?> registerClient(@RequestBody ClientDto dto, UriComponentsBuilder builder) {
        var clientToRegister = new Client(
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        deleteClient.deleteClient(id);
        return ResponseEntity.ok("Client with ID " + id + " deleted successfully.");
    }

}



