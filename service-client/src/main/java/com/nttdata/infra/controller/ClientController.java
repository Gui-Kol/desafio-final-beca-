package com.nttdata.infra.controller;

import com.nttdata.application.usecases.client.*;
import com.nttdata.domain.client.Client;
import com.nttdata.infra.controller.dtos.client.ClientDto;
import com.nttdata.infra.controller.dtos.client.ClientUpdateDto;
import jakarta.validation.Valid;
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
    private final UpdateClient updateClient;


    public ClientController(ListClients listClients, RegisterClientRoleClient registerClientRoleClient, DeleteClient deleteClient, FindClientByCpf findClientByCpf, UpdateClient updateClient) {
        this.listClients = listClients;
        this.registerClientRoleClient = registerClientRoleClient;
        this.deleteClient = deleteClient;
        this.updateClient = updateClient;
    }

    @GetMapping
    public ResponseEntity listClients() {
        var list = listClients.clientList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> registerClient(@RequestBody @Valid ClientDto dto, UriComponentsBuilder builder) {
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
    //ESTA DELETANDO DO BANCO DE DADOS, ALTERAR PARA DEIXAR O CLIENT COMO INATIVO
    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        deleteClient.deleteClient(id);
        return ResponseEntity.ok("Client with ID " + id + " deleted successfully.");
    }


    @PutMapping("/{id}")
    public ResponseEntity updateClient(@RequestBody ClientUpdateDto dto, @PathVariable Long id) {
        var client = new Client(null,
                dto.name(),dto.email(),
                dto.address(),dto.username(),
                dto.password(),dto.cpf(),
                dto.birthDay(),dto.telephone(),
                null,LocalDate.now(),
                true, null
        );
        var clientUpdated = updateClient.update(client, id);
        return ResponseEntity.ok("Client updated successfully: " + clientUpdated);
    }


}



