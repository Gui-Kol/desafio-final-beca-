package com.nttdata.infra.controller.dtos;

import com.nttdata.domain.address.Address;

import java.time.LocalDate;
import java.util.Set;

public record ClientDto(
        Long id,
        String name,
        String email,
        Address address,
        String username,
        String password,
        String cpf,
        LocalDate birthDay,
        String telephone,
        Set<String> roles
        ) {
}
