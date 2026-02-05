package com.nttdata.domain.client;

import com.nttdata.domain.address.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Client {
    private Long id;
    private String name;
    private String email;
    private Address address;
    private String username;
    private String password; //adicionar ao banco de dados no formado de BCrypt
    private String cpf;
    private LocalDate birthDay;
    private String telephone;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private boolean active;
    private LocalDateTime lastLoginDate;
    private int loginAttempts;

    public Client(Long id, String name, String email, Address address, String username, String password, String cpf, LocalDate birthDay, String telephone, LocalDate creationDate, LocalDate lastUpdateDate, boolean active, LocalDateTime lastLoginDate, int loginAttempts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.birthDay = birthDay;
        this.telephone = telephone;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.active = active;
        this.lastLoginDate = lastLoginDate;
        this.loginAttempts = loginAttempts;
    }

    public Client() {}

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

    public String getPassword() {
        return password;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", birthDay=" + birthDay +
                ", telephone='" + telephone + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", active=" + active +
                ", lastLoginDate=" + lastLoginDate +
                '}';
    }
}

