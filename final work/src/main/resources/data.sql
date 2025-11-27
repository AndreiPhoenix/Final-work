-- Инициализационные данные (опционально)
INSERT INTO currency_rates (currency_name, currency_code, rate_to_rub, rate_to_usd, change_24h, source, timestamp)
VALUES
('US Dollar', 'USD', 90.5, 1.0, 0.5, 'INIT', CURRENT_TIMESTAMP),
('Euro', 'EUR', 98.2, 1.08, -0.3, 'INIT', CURRENT_TIMESTAMP),
('Bitcoin', 'BTC', 4200000.0, 46500.0, 2.1, 'INIT', CURRENT_TIMESTAMP),
('Ethereum', 'ETH', 250000.0, 2750.0, 1.5, 'INIT', CURRENT_TIMESTAMP);