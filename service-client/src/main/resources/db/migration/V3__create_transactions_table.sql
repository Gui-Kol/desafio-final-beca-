-- V3__create_transactions_table
CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    source_account_id BIGINT NOT NULL,
    destination_account_id BIGINT,
    value NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    applied_exchange_rate NUMERIC(10, 6),
    converted_value NUMERIC(19, 2),
    description VARCHAR(255),
    transaction_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    method VARCHAR(50) NOT NULL
);