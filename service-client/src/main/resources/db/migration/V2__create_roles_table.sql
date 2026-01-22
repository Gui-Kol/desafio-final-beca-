-- V2__create_roles_table.sql
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    role_name VARCHAR(50) NOT NULL,

    -- Restrição de unicidade composta para garantir que um cliente não tenha a mesma role duas vezes
    CONSTRAINT uk_client_id_role_name UNIQUE (client_id, role_name)
);