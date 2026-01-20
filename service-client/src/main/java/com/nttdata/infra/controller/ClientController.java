package com.nttdata.infra.controller;

import com.nttdata.application.usecases.ListClients;
import com.nttdata.domain.client.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ListClients listClients;

    public ClientController(ListClients listClients) {
        this.listClients = listClients;
    }

    @GetMapping
    public ResponseEntity listClients() {
        var list = listClients.clientList();
        return ResponseEntity.ok(list);
    }


}
