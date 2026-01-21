package com.nttdata.infra.persistence.role;

import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.infra.exceptions.UpdateFailException;
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

    public void delete(){
        this.name = RoleName.ROLE_INACTIVE;
    }

    public void update(Long clientId, RoleName name) {
        if (clientId != null || name != null){
            this.name = name;
        }else {
            throw new UpdateFailException("The attributes to update the Role cannot be null.");
        }
    }


    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", name=" + name +
                '}';
    }
}
