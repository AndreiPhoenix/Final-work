package com.example.currencyparser.controller;

import com.example.currencyparser.model.CurrencyRate;
import com.example.currencyparser.model.ParseRequest;
import com.example.currencyparser.service.CurrencyProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/api/currency")
@CrossOrigin(origins = "*")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyProcessingService processingService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Currency Parser Service is running!");
        return "index";
    }

    @PostMapping("/parse")
    @ResponseBody
    public CompletableFuture<ResponseEntity<?>> parseCurrencyRates(@RequestBody ParseRequest request) {
        logger.info("Получен запрос на парсинг {} URL", request.getUrls().size());

        return processingService.processMultipleSources(request.getUrls(), request.isSaveToDatabase())
                .thenApply(rates -> {
                    if (rates.isEmpty()) {
                        return ResponseEntity.badRequest()
                                .body("Не удалось получить данные с указанных URL. Проверьте доступность источников.");
                    }
                    return ResponseEntity.ok(rates);
                })
                .exceptionally(ex -> {
                    logger.error("Ошибка при обработке запроса: {}", ex.getMessage());
                    return ResponseEntity.internalServerError()
                            .body("Произошла ошибка при обработке запроса: " + ex.getMessage());
                });
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<CurrencyRate>> getAllRates() {
        List<CurrencyRate> rates = processingService.getAllRates();
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/latest")
    @ResponseBody
    public ResponseEntity<List<CurrencyRate>> getLatestRates() {
        List<CurrencyRate> rates = processingService.getLatestRates();
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/source/{source}")
    @ResponseBody
    public ResponseEntity<List<CurrencyRate>> getRatesBySource(@PathVariable String source) {
        List<CurrencyRate> rates = processingService.getRatesBySource(source);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/currency/{currencyCode}")
    @ResponseBody
    public ResponseEntity<List<CurrencyRate>> getRatesByCurrencyCode(@PathVariable String currencyCode) {
        List<CurrencyRate> rates = processingService.getRatesByCurrencyCode(currencyCode);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/base/{baseCurrency}")
    @ResponseBody
    public ResponseEntity<List<CurrencyRate>> getRatesByBaseCurrency(@PathVariable String baseCurrency) {
        List<CurrencyRate> rates = processingService.getRatesByBaseCurrency(baseCurrency);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("/health")
    @ResponseBody
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Currency Parser Service is running");
    }
}