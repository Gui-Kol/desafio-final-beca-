package com.nttdata.infra.presentation.dtos.client;

import com.nttdata.infra.presentation.dtos.address.AddressDto;

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
