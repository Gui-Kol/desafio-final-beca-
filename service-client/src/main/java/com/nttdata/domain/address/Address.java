package com.nttdata.domain.address;

public class Address {
    private String street;
    private String number;
    private String addressDetails; // complemento
    private String neighborhood;
    private String city;
    private String state;
    private String postcode; //CEP
    private String country;

    public Address(String street, String number, String addressDetails, String neighborhood, String city, String state, String postcode, String country) {
        this.street = street;
        this.number = number;
        this.addressDetails = addressDetails;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }
}
