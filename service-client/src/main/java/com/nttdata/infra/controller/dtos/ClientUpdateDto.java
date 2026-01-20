package com.nttdata.infra.controller.dtos;

import com.nttdata.domain.address.Address;

import java.time.LocalDate;

public record ClientUpdateDto(
        String name,
        String email,
        Address address,
        String username,
        String password,
        String cpf,
        LocalDate birthDay,
        String telephone,
        boolean active
) {
}
