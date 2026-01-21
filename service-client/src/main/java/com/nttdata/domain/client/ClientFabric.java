package com.nttdata.domain.client;

import com.nttdata.domain.address.Address;

import java.time.LocalDate;

public class ClientFabric {

    public Client fabric(String name, String email, String username, String password, String cpf, LocalDate birthDay, String telephone,
                         String street, String number, String addressDetails, String neighborhood, String city, String state, String postcode, String country) {

        var address = new Address(street, number, addressDetails, neighborhood, city, state, postcode, country);

        return new Client(name, email, address, username, password, cpf, birthDay, telephone, LocalDate.now(), true);
    }


}
