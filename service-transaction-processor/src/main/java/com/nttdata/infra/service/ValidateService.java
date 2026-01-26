package com.nttdata.infra.service;

import com.nttdata.domain.bank.Bank;
import com.nttdata.domain.bank.BankFactory;
import com.nttdata.domain.bank.BankDto;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class ValidateService {
    private final RestTemplate restTemplate;
    private final BankFactory bankFactory;

    public ValidateService(RestTemplate restTemplate, BankFactory bankFactory) {
        this.restTemplate = restTemplate;
        this.bankFactory = bankFactory;
    }

    public BankDto valid(Long id) {
        String url = "http://6973d075b5f46f8b5828534e.mockapi.io/mockapi/bank";

        try {
            return restTemplate.getForObject(url + "/" + id, BankDto.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            Bank ramdomBank = bankFactory.createForId(id);

            return restTemplate.postForObject(url, ramdomBank, BankDto.class);
        }
    }
}
