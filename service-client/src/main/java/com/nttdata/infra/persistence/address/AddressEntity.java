package com.nttdata.infra.persistence.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Embeddable
public class AddressEntity {
    private String street;
    private String number;
    private String addressDetails; // complemento
    private String neighborhood;
    private String city;
    private String state;
    private String postcode; //CEP
    private String country;
}
