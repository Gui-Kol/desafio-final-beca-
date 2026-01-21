package com.nttdata.infra.controller.dtos.client;

import com.nttdata.domain.address.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClientDto(
        @NotNull(message = "Name is mandatory.")
        String name,
        @Email
        String email,
        @Valid
        @NotNull
        Address address,
        @NotNull(message = "Username is mandatory.")
        String username,
        @NotNull(message = "Password is mandatory.")
        String password,
        @CPF
        String cpf,
        @NotNull(message = "Birth Day is mandatory.")
        LocalDate birthDay,
        String telephone
) {
}
