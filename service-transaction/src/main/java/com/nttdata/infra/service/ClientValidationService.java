package com.nttdata.infra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientValidationService {
    private final RestTemplate restTemplate;
    private final String clientsServiceBaseUrl;

    public ClientValidationService(RestTemplate restTemplate,@Value("http://localhost:8081") String clientsServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.clientsServiceBaseUrl = clientsServiceBaseUrl;
    }
    public boolean checkClientExistence(Long clientId) {
        String url = clientsServiceBaseUrl + "/clients/{id}/exists";

        try {
            Boolean exists = restTemplate.getForObject(url, Boolean.class, clientId);

            if(exists == null){
                exists = false;
            }
            return exists;

        } catch (RestClientException e) {
            System.err.println("Erro ao se comunicar com o microsserviço de clientes para o ID " + clientId + ": " + e.getMessage());
            return false;
        }
    }
}