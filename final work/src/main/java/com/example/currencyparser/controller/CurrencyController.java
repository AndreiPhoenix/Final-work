package com.example.currencyparser.controller;

import com.example.currencyparser.repository.entity.CurrencyRate;
import com.example.currencyparser.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rates")
    public ResponseEntity<List<CurrencyRate>> getAllRates(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order) {

        List<CurrencyRate> rates = currencyService.getAllRates(sortBy, order);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/rates/{currencyCode}")
    public ResponseEntity<List<CurrencyRate>> getRatesByCurrency(
            @PathVariable String currencyCode) {

        List<CurrencyRate> rates = currencyService.getRatesByCurrency(currencyCode);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/rates/date")
    public ResponseEntity<List<CurrencyRate>> getRatesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<CurrencyRate> rates = currencyService.getRatesByDate(date);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/rates/specific")
    public ResponseEntity<List<CurrencyRate>> getLatestForSpecificCurrencies(
            @RequestParam List<String> currencies) {

        List<CurrencyRate> rates = currencyService.getLatestRatesForSpecificCurrencies(currencies);
        return ResponseEntity.ok(rates);
    }
}