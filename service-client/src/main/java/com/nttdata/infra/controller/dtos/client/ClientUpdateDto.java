package com.nttdata.infra.controller.dtos.client;

import com.nttdata.infra.controller.dtos.address.AddressDto;

import java.time.LocalDate;

public record ClientUpdateDto(
        String name,
        String email,
        AddressDto address,
        String username,
        String password,
        String cpf,
        LocalDate birthDay,
        String telephone
) {
}
