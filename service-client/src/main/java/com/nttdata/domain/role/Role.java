package com.nttdata.domain.role;

public class Role {
    private long id;
    private long clientId;
    private RoleName name;

    public Role(long clientId, RoleName name) {
        this.clientId = clientId;
        this.name = name;
    }

    public Role(Long id, Long clientId, RoleName name) {
        this.id = id;
        this.clientId = clientId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }

    public long getClientId() {
        return clientId;
    }
}
