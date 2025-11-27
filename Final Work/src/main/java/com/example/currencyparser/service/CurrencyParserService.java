package com.example.currencyparser.service;

import com.example.currencyparser.model.CurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CurrencyParserService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyParserService.class);

    public CompletableFuture<List<CurrencyRate>> parseCurrencyRates(String url) {
        return CompletableFuture.supplyAsync(() -> {
            List<CurrencyRate> rates = new ArrayList<>();
            try {
                logger.info("Начинаем парсинг URL: {}", url);

                // Временное решение - возвращаем тестовые данные
                if (url.contains("cbr.ru")) {
                    rates = generateCbrTestData();
                } else if (url.contains("coinmarketcap.com")) {
                    rates = generateCryptoTestData();
                } else if (url.contains("finance.yahoo.com")) {
                    rates = generateForexTestData();
                } else {
                    logger.warn("Неизвестный источник: {}", url);
                }

                logger.info("Успешно сгенерировано {} тестовых курсов валют для {}", rates.size(), url);

            } catch (Exception e) {
                logger.error("Ошибка при обработке URL {}: {}", url, e.getMessage());
            }
            return rates;
        });
    }

    private List<CurrencyRate> generateCbrTestData() {
        List<CurrencyRate> rates = new ArrayList<>();

        rates.add(new CurrencyRate("Доллар США", "USD",
                new BigDecimal("90.50"), "RUB", new BigDecimal("0.8"), "CBR"));
        rates.add(new CurrencyRate("Евро", "EUR",
                new BigDecimal("98.75"), "RUB", new BigDecimal("0.5"), "CBR"));
        rates.add(new CurrencyRate("Китайский юань", "CNY",
                new BigDecimal("12.45"), "RUB", new BigDecimal("0.3"), "CBR"));
        rates.add(new CurrencyRate("Японская йена", "JPY",
                new BigDecimal("0.62"), "RUB", new BigDecimal("-0.2"), "CBR"));
        rates.add(new CurrencyRate("Фунт стерлингов", "GBP",
                new BigDecimal("115.20"), "RUB", new BigDecimal("0.7"), "CBR"));

        return rates;
    }

    private List<CurrencyRate> generateCryptoTestData() {
        List<CurrencyRate> rates = new ArrayList<>();

        rates.add(new CurrencyRate("Bitcoin", "BTC",
                new BigDecimal("45000.00"), "USD", new BigDecimal("2.5"), "CoinMarketCap"));
        rates.add(new CurrencyRate("Ethereum", "ETH",
                new BigDecimal("2500.00"), "USD", new BigDecimal("1.8"), "CoinMarketCap"));
        rates.add(new CurrencyRate("Binance Coin", "BNB",
                new BigDecimal("320.00"), "USD", new BigDecimal("0.5"), "CoinMarketCap"));
        rates.add(new CurrencyRate("Cardano", "ADA",
                new BigDecimal("0.45"), "USD", new BigDecimal("-1.2"), "CoinMarketCap"));
        rates.add(new CurrencyRate("Solana", "SOL",
                new BigDecimal("95.00"), "USD", new BigDecimal("3.1"), "CoinMarketCap"));

        return rates;
    }

    private List<CurrencyRate> generateForexTestData() {
        List<CurrencyRate> rates = new ArrayList<>();

        rates.add(new CurrencyRate("USD/RUB", "USDRUB",
                new BigDecimal("90.50"), "RUB", new BigDecimal("0.8"), "YahooFinance"));
        rates.add(new CurrencyRate("EUR/RUB", "EURRUB",
                new BigDecimal("98.75"), "RUB", new BigDecimal("0.5"), "YahooFinance"));
        rates.add(new CurrencyRate("USD/EUR", "USDEUR",
                new BigDecimal("0.92"), "EUR", new BigDecimal("-0.2"), "YahooFinance"));
        rates.add(new CurrencyRate("USD/JPY", "USDJPY",
                new BigDecimal("145.50"), "JPY", new BigDecimal("0.3"), "YahooFinance"));
        rates.add(new CurrencyRate("GBP/USD", "GBPUSD",
                new BigDecimal("1.2650"), "USD", new BigDecimal("0.1"), "YahooFinance"));

        return rates;
    }
}