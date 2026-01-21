package com.nttdata.domain.client;

import com.nttdata.domain.address.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ClientFactory {

    public Client factory(String name, String email, String username, String password, String cpf, LocalDate birthDay, String telephone,
                          String street, String number, String addressDetails, String neighborhood, String city, String state, String postcode, String country) {

        var address = new Address(street, number, addressDetails, neighborhood, city, state, postcode, country);
        var loginDate = LocalDateTime.of(LocalDate.ofYearDay(0, 1), LocalTime.ofSecondOfDay(0));
        var updateDate = LocalDate.ofYearDay(0, 1);
        var createDate = LocalDate.now();

        return new Client(null,name, email, address, username, password, cpf, birthDay, telephone,createDate,updateDate , true, loginDate);
    }


}
