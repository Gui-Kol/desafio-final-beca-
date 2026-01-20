package com.nttdata.infra.persistence.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AddressEntity {
    @Column(name = "address_street")
    private String street;
    @Column(name = "address_number")
    private String number;
    @Column(name = "address_address_details")
    private String addressDetails;
    @Column(name = "address_neighborhood")
    private String neighborhood;
    @Column(name = "address_city")
    private String city;
    @Column(name = "address_state")
    private String state;
    @Column(name = "address_postcode")
    private String postcode;
    @Column(name = "address_country")    //CEP
    private String country;
}
