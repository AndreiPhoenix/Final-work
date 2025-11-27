package com.example.currencyparser.service;

import com.example.currencyparser.repository.entity.CurrencyRate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebClientService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public WebClientService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public List<CurrencyRate> parseCurrencyRates(String sourceUrl) {
        try {
            if (sourceUrl.contains("cbr-xml-daily.ru")) {
                return parseCbrRates(sourceUrl);
            } else if (sourceUrl.contains("coingecko.com")) {
                return parseCryptoRates(sourceUrl);
            }
        } catch (Exception e) {
            log.error("Error parsing URL: {}", sourceUrl, e);
        }
        return new ArrayList<>();
    }

    private List<CurrencyRate> parseCbrRates(String url) {
        try {
            String jsonResponse = webClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode valutes = root.path("Valute");

            List<CurrencyRate> rates = new ArrayList<>();
            valutes.fields().forEachRemaining(entry -> {
                JsonNode valute = entry.getValue();
                CurrencyRate rate = new CurrencyRate(
                        valute.path("Name").asText(),
                        valute.path("CharCode").asText(),
                        valute.path("Value").asDouble(),
                        null, // Для ЦБ РФ курс к USD не предоставляется напрямую
                        valute.path("Previous").asDouble() - valute.path("Value").asDouble(),
                        "CBR"
                );
                rates.add(rate);
            });

            return rates;
        } catch (Exception e) {
            log.error("Error parsing CBR rates", e);
            return new ArrayList<>();
        }
    }

    private List<CurrencyRate> parseCryptoRates(String url) {
        try {
            String jsonResponse = webClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = objectMapper.readTree(jsonResponse);
            List<CurrencyRate> rates = new ArrayList<>();

            // Парсинг Bitcoin
            if (root.has("bitcoin")) {
                JsonNode bitcoin = root.path("bitcoin");
                rates.add(createCryptoRate("Bitcoin", "BTC", bitcoin));
            }

            // Парсинг Ethereum
            if (root.has("ethereum")) {
                JsonNode ethereum = root.path("ethereum");
                rates.add(createCryptoRate("Ethereum", "ETH", ethereum));
            }

            return rates;
        } catch (Exception e) {
            log.error("Error parsing crypto rates", e);
            return new ArrayList<>();
        }
    }

    private CurrencyRate createCryptoRate(String name, String code, JsonNode data) {
        return new CurrencyRate(
                name,
                code,
                data.path("rub").asDouble(),
                data.path("usd").asDouble(),
                data.path("rub_24h_change").asDouble(),
                "CoinGecko"
        );
    }
}