package com.nttdata.infra.presentation.dtos.client;

import com.nttdata.infra.presentation.dtos.address.AddressDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClientUpdateDto(
        @NotBlank
        String name,
        @Email
        String email,
        @Valid
        @NotNull
        AddressDto address,
        @NotBlank
        String username,
        @NotBlank
        String password,
        @CPF
        String cpf,
        LocalDate birthDay,
        @NotBlank
        String telephone
) {
}
