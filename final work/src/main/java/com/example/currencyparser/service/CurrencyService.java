package com.example.currencyparser.service;

import com.example.currencyparser.repository.CurrencyRepository;
import com.example.currencyparser.repository.entity.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ConcurrentMap<String, List<CurrencyRate>> cache = new ConcurrentHashMap<>();

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyRate> getAllRates(String sortBy, String order) {
        List<CurrencyRate> rates = currencyRepository.findAll();

        return rates.parallelStream()
                .sorted(createComparator(sortBy, order))
                .collect(Collectors.toList());
    }

    public List<CurrencyRate> getRatesByCurrency(String currencyCode) {
        return currencyRepository.findByCurrencyCodeOrderByTimestampDesc(currencyCode);
    }

    public List<CurrencyRate> getLatestRatesForSpecificCurrencies(List<String> currencyCodes) {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return currencyRepository.findLatestByCodes(currencyCodes, yesterday);
    }

    public List<CurrencyRate> getRatesByDate(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return currencyRepository.findByTimestampBetweenOrderByTimestampDesc(startOfDay, endOfDay);
    }

    private Comparator<CurrencyRate> createComparator(String sortBy, String order) {
        Comparator<CurrencyRate> comparator = switch (sortBy != null ? sortBy.toLowerCase() : "timestamp") {
            case "currencyname" -> Comparator.comparing(CurrencyRate::getCurrencyName);
            case "rate" -> Comparator.comparing(CurrencyRate::getRateToRub);
            case "change" -> Comparator.comparing(CurrencyRate::getChange24h);
            default -> Comparator.comparing(CurrencyRate::getTimestamp);
        };

        return "desc".equalsIgnoreCase(order) ? comparator.reversed() : comparator;
    }
}