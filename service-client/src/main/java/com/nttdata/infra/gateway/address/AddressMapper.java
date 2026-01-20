package com.nttdata.infra.gateway.address;

import com.nttdata.domain.address.Address;
import com.nttdata.infra.persistence.address.AddressEntity;

public class AddressMapper {
    public Address address(AddressEntity entity){
        return new Address(entity.getStreet(), entity.getNumber(), entity.getAddressDetails(),
                entity.getNeighborhood(), entity.getCity(), entity.getState(),
                entity.getPostcode(), entity.getCountry());
    }

    public AddressEntity entity (Address address){
        return new AddressEntity(address.getStreet(), address.getNumber(), address.getAddressDetails(),
                address.getNeighborhood(), address.getCity(), address.getState(),
                address.getPostcode(), address.getCountry());
    }


}
