package com.nttdata.infra.controller.dtos.address;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        @NotBlank
        String street,
        String number,
        String addressDetails,
        @NotBlank
        String neighborhood,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        String postcode,
        @NotBlank//CEP
        String country
) {
}
