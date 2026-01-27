package com.nttdata.infra.presentation.controller;

import com.nttdata.infra.exception.newexception.ClientInactiveException;
import com.nttdata.infra.exception.newexception.ClientNotExistsException;
import com.nttdata.infra.exception.newexception.RegistryClientException;
import com.nttdata.application.usecase.client.*;
import com.nttdata.domain.client.ClientFactory;
import com.nttdata.infra.presentation.dtos.client.ClientDto;
import com.nttdata.infra.presentation.dtos.client.ClientUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ListClients listClients;
    private final ClientFactory clientFactory;
    private final RegisterClientRoleClient registerClientRoleClient;
    private final DeleteClient deleteClient;
    private final UpdateClient updateClient;
    private final ExistsClient existsClient;
    private final VerifyClientActive verifyClientActive;

    public ClientController(ListClients listClients, ClientFactory clientFactory, RegisterClientRoleClient registerClientRoleClient, DeleteClient deleteClient, UpdateClient updateClient, ExistsClient existsClient, VerifyClientActive verifyClientActive) {
        this.listClients = listClients;
        this.clientFactory = clientFactory;
        this.registerClientRoleClient = registerClientRoleClient;
        this.deleteClient = deleteClient;
        this.updateClient = updateClient;
        this.existsClient = existsClient;
        this.verifyClientActive = verifyClientActive;
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity checkClientExists(@PathVariable Long id) {
        try {
            boolean exists = existsClient.byId(id);
            boolean active = verifyClientActive.verifyById(id);
            boolean response;
            if (exists && active) {
                response = true;
            } else {
                response = false;
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ClientNotExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity listClients() {
        try {
            var list = listClients.clientList();
            return ResponseEntity.ok(list);
        } catch (ClientNotExistsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity registerClient(@RequestBody @Valid ClientDto dto, UriComponentsBuilder builder) {
        var clientToRegister = clientFactory.factory(dto.name(), dto.email(), dto.username(), dto.password(), dto.cpf(), dto.birthDay(), dto.telephone(),
                dto.address().street(), dto.address().number(), dto.address().addressDetails(), dto.address().neighborhood(), dto.address().city(),
                dto.address().state(), dto.address().postcode(), dto.address().country());

        try {
            var registeredClient = registerClientRoleClient.register(clientToRegister);
            var uri = builder.path("/clients/{id}").buildAndExpand(registeredClient.getId()).toUri();
            return ResponseEntity.created(uri).body(registeredClient);
        } catch (RegistryClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        try {
            deleteClient.deleteClient(id);
            return ResponseEntity.ok("Client with ID " + id + " deleted successfully.");
        }catch (ClientInactiveException | ClientNotExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity updateClient(@RequestBody @Valid ClientUpdateDto dto, @PathVariable Long id) {
        var client = clientFactory.factory(dto.name(), dto.email(), dto.username(), dto.password(), dto.cpf(), dto.birthDay(), dto.telephone(),
                dto.address().street(), dto.address().number(), dto.address().addressDetails(), dto.address().neighborhood(), dto.address().city(),
                dto.address().state(), dto.address().postcode(), dto.address().country());
        try {
            var clientUpdated = updateClient.update(client, id);
            return ResponseEntity.ok(clientUpdated);
        }catch (ClientNotExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}



