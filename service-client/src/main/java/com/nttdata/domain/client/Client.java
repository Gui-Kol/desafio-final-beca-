package com.nttdata.domain.client;

import com.nttdata.domain.address.Address;
import com.nttdata.domain.roles.RoleName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Client {
    private Long id;
    private String name;
    private String email;
    private Address address;
    private String username;
    private String passsword; //adicionar ao banco de dados no formado de BCrypt
    private String cpf;
    private LocalDate birthDay;
    private String telephone;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private boolean active;
    private RoleName role;
    private LocalDateTime lastLoginDate;

    public Client(Long id, String name, String email, Address address, String username, String passsword, String cpf, LocalDate birthDay, String telephone, LocalDate creationDate, LocalDate lastUpdateDate, boolean active, RoleName role, LocalDateTime lastLoginDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.username = username;
        this.passsword = passsword;
        this.cpf = cpf;
        this.birthDay = birthDay;
        this.telephone = telephone;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.active = active;
        this.role = role;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPasssword() {
        return passsword;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getTelephone() {
        return telephone;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }
    public RoleName getRole() {
        return role;
    }
}

