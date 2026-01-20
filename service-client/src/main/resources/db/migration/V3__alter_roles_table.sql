CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL, -- Coluna para o ID do cliente
    role_name VARCHAR(50) NOT NULL, -- O nome da role (ex: 'ROLE_CLIENT')

    -- Restrição de unicidade composta para garantir que um cliente não tenha a mesma role duas vezes
    CONSTRAINT uk_client_id_role_name UNIQUE (client_id, role_name)
);