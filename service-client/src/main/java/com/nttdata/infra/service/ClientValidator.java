package com.nttdata.infra.service;

import com.nttdata.infra.presentation.dtos.client.ClientDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ClientValidator {
    public void validate(@Valid ClientDto dto) {}
}
