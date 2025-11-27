package com.example.currencyparser.service;

import com.example.currencyparser.repository.CurrencyRepository;
import com.example.currencyparser.repository.entity.CurrencyRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ParserService {

    private final CurrencyRepository currencyRepository;
    private final WebClientService webClientService;
    private final ExecutorService customExecutor;

    @Autowired
    public ParserService(CurrencyRepository currencyRepository, WebClientService webClientService) {
        this.currencyRepository = currencyRepository;
        this.webClientService = webClientService;
        this.customExecutor = Executors.newFixedThreadPool(5);
    }

    public List<CurrencyRate> parseMultipleSources(List<String> sources) {
        List<CompletableFuture<List<CurrencyRate>>> futures = sources.stream()
                .map(source -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return webClientService.parseCurrencyRates(source);
                    } catch (Exception e) {
                        log.error("Error parsing source: {}", source, e);
                        return Collections.<CurrencyRate>emptyList();
                    }
                }, customExecutor))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();
    }

    public void saveCurrencyRates(List<CurrencyRate> rates) {
        if (rates != null && !rates.isEmpty()) {
            // Используем parallelStream для параллельного сохранения
            List<CurrencyRate> savedRates = rates.parallelStream()
                    .map(currencyRepository::save)
                    .toList();

            log.info("Saved {} currency rates", savedRates.size());
        }
    }

    public Map<String, List<CurrencyRate>> parseAndSaveScheduled() {
        List<String> sources = Arrays.asList(
                "https://www.cbr-xml-daily.ru/daily_json.js",
                "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=rub,usd&include_24h_change=true"
        );

        List<CurrencyRate> rates = parseMultipleSources(sources);
        saveCurrencyRates(rates);

        // Группируем по валютам для возврата
        Map<String, List<CurrencyRate>> result = new ConcurrentHashMap<>();
        rates.parallelStream()
                .forEach(rate -> {
                    result.computeIfAbsent(rate.getCurrencyCode(), k -> new CopyOnWriteArrayList<>())
                            .add(rate);
                });

        return result;
    }
}