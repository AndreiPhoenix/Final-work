package com.example.currencyparser.service;

import com.example.currencyparser.model.CurrencyRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CurrencyProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyProcessingService.class);

    @Autowired
    private CurrencyParserService parserService;

    @Autowired
    private CurrencyRateRepository repository;

    @Async("taskExecutor")
    public CompletableFuture<List<CurrencyRate>> processMultipleSources(List<String> urls, boolean saveToDatabase) {
        logger.info("Начинаем обработку {} источников", urls.size());

        List<CompletableFuture<List<CurrencyRate>>> futures = urls.stream()
                .map(parserService::parseCurrencyRates)
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<CurrencyRate> allRates = futures.stream()
                            .map(CompletableFuture::join)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());

                    logger.info("Всего собрано курсов валют: {}", allRates.size());

                    if (saveToDatabase && !allRates.isEmpty()) {
                        try {
                            List<CurrencyRate> savedRates = repository.saveAll(allRates);
                            logger.info("Сохранено {} записей в базу данных", savedRates.size());
                        } catch (Exception e) {
                            logger.error("Ошибка при сохранении в базу данных: {}", e.getMessage());
                        }
                    }

                    return allRates;
                });
    }

    public List<CurrencyRate> getAllRates() {
        return repository.findAll();
    }

    public List<CurrencyRate> getRatesBySource(String source) {
        return repository.findBySource(source);
    }

    public List<CurrencyRate> getLatestRates() {
        return repository.findLatestRates();
    }

    public List<CurrencyRate> getRatesByCurrencyCode(String currencyCode) {
        return repository.findByCurrencyCode(currencyCode);
    }

    public List<CurrencyRate> getRatesByBaseCurrency(String baseCurrency) {
        return repository.findByBaseCurrency(baseCurrency);
    }
}