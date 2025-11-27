CREATE TABLE IF NOT EXISTS currency_rates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency_name VARCHAR(255) NOT NULL,
    currency_code VARCHAR(10) NOT NULL,
    rate_to_rub DOUBLE NOT NULL,
    rate_to_usd DOUBLE,
    change_24h DOUBLE NOT NULL,
    source VARCHAR(100) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);