package com.nttdata.infra.persistence.client;

import com.nttdata.infra.persistence.address.AddressEntity;
import com.nttdata.infra.persistence.role.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private String password; //adicionar ao banco de dados no formado de BCrypt
    private String cpf;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    private String telephone;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "last_update_date")
    private LocalDate lastUpdateDate;
    private boolean active;
    @ManyToMany
    @JoinTable(name = "roles")
    private Set<RoleEntity> role = new HashSet<>();;
    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;


}
