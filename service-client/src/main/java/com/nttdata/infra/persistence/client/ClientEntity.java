package com.nttdata.infra.persistence.client;

import com.nttdata.domain.roles.RoleName;
import com.nttdata.infra.persistence.address.AddressEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "clients")
@Entity(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
    private String passsword; //adicionar ao banco de dados no formado de BCrypt
    private String cpf;
    private LocalDate birthDay;
    private String telephone;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private boolean active;
    private RoleName role;
    private LocalDateTime lastLoginDate;


}
