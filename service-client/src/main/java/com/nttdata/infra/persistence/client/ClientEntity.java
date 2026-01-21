package com.nttdata.infra.persistence.client;

import com.nttdata.domain.address.Address;
import com.nttdata.domain.client.Client;
import com.nttdata.infra.persistence.address.AddressEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "clients")
@Entity(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Embedded
    private AddressEntity address;
    private String username;
    private String password; //adicionar ao banco de dados no formado de BCrypt
    private String cpf;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    private String telephone;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;
    @Setter
    private boolean active;
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;


    public void update(Client client) {
        if (client == null) {
            throw new RuntimeException("Client cannot be null to update.");
        }
        if (client.getEmail() != null) {
            this.email = client.getEmail();
        }
        if (client.getName() != null) {
            this.name = client.getName();
        }
        if (client.getUsername() != null) {
            this.username = client.getUsername();
        }
        if (client.getPassword() != null) {
            this.password = client.getPassword();
        }
        if (client.getCpf() != null) {
            this.cpf = client.getCpf();
        }
        if (client.getBirthDay() != null) {
            this.birthDay = client.getBirthDay();
        }
        if (client.getTelephone() != null) {
            this.telephone = client.getTelephone();
        }
        if (client.getAddress() != null) {
            updateAddress(client);
        }

    }

    private void updateAddress(Client client) {
        Address sourceAddressData = client.getAddress();
        AddressEntity targetAddress = this.address;

        if (sourceAddressData.getAddressDetails() != null) {
            targetAddress.setAddressDetails(sourceAddressData.getAddressDetails());
        }
        if (sourceAddressData.getCity() != null) {
            targetAddress.setCity(sourceAddressData.getCity());
        }
        if (sourceAddressData.getCountry() != null) {
            targetAddress.setCountry(sourceAddressData.getCountry());
        }
        if (sourceAddressData.getNumber() != null) {
            targetAddress.setNumber(sourceAddressData.getNumber());
        }
        if (sourceAddressData.getNeighborhood() != null) {
            targetAddress.setNeighborhood(sourceAddressData.getNeighborhood());
        }
        if (sourceAddressData.getPostcode() != null) {
            targetAddress.setPostcode(sourceAddressData.getPostcode());
        }
        if (sourceAddressData.getState() != null) {
            targetAddress.setState(sourceAddressData.getState());
        }
        if (sourceAddressData.getStreet() != null) {
            targetAddress.setStreet(sourceAddressData.getStreet());
        }
    }
}
