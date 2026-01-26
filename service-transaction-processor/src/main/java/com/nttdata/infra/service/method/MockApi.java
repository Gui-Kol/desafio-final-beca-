package com.nttdata.infra.service.method;

import com.nttdata.domain.bank.Bank;
import com.nttdata.domain.bank.BankDto;
import com.nttdata.infra.service.Validate;
import org.springframework.web.client.RestTemplate;

public class MockApi {
    private final RestTemplate restTemplate;
    private final Validate validate;


    public MockApi(RestTemplate restTemplate, Validate validate) {
        this.restTemplate = restTemplate;
        this.validate = validate;
    }

    public void put(String url, Bank destinationBankUpdated) {
        restTemplate.put(url + destinationBankUpdated.getId(), destinationBankUpdated);
    }

    public BankDto get(Long id){
        return validate.valid(id);
    }

}
