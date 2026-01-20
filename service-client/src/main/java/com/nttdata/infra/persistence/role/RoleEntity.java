package com.nttdata.infra.persistence.role;

import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "roles")
@Entity(name = "role")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private long clientId;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName name;

    public RoleEntity(Role roles) {
        this.clientId = roles.getClientId();
        this.name = roles.getName();
    }
}
