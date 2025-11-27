package com.example.currencyparser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ScheduledParserService {

    private final ParserService parserService;
    private final AtomicInteger executionCount = new AtomicInteger(0);

    @Autowired
    public ScheduledParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Scheduled(fixedRate = 300000) // Каждые 5 минут
    public void scheduledParsing() {
        int count = executionCount.incrementAndGet();
        log.info("Starting scheduled parsing #{}", count);

        try {
            var result = parserService.parseAndSaveScheduled();
            log.info("Scheduled parsing #{} completed. Parsed {} currency groups", count, result.size());
        } catch (Exception e) {
            log.error("Error during scheduled parsing #{}", count, e);
        }
    }

    @Scheduled(initialDelay = 10000, fixedRate = 60000) // Первый запуск через 10 сек, затем каждую минуту для демо
    public void quickDemoParsing() {
        log.info("Quick demo parsing started");
        try {
            var result = parserService.parseAndSaveScheduled();
            log.info("Demo parsing completed. Currency groups: {}", result.keySet());
        } catch (Exception e) {
            log.error("Error during demo parsing", e);
        }
    }
}