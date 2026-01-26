package com.nttdata.infra.service;

import com.nttdata.domain.bank.Bank;
import com.nttdata.domain.bank.BankDto;
import org.springframework.web.client.RestTemplate;

public class MockApiService {
    private final RestTemplate restTemplate;
    private final ValidateService validateService;


    public MockApiService(RestTemplate restTemplate, ValidateService validateService) {
        this.restTemplate = restTemplate;
        this.validateService = validateService;
    }

    public void put(String url, Bank destinationBankUpdated) {
        restTemplate.put(url + destinationBankUpdated.getId(), destinationBankUpdated);
    }

    public BankDto get(Long id){
        return validateService.valid(id);
    }

}
