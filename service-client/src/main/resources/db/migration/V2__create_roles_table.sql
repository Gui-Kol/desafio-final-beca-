-- V2__create_roles_table.sql

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE -- Nome do papel (ex: 'ROLE_ADMIN', 'ROLE_CLIENT'), deve ser único
);