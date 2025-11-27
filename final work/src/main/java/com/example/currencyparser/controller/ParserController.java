package com.example.currencyparser.controller;

import com.example.currencyparser.service.ParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/parser")
public class ParserController {

    private final ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startParsing() {
        log.info("Manual parsing started via API");

        try {
            var result = parserService.parseAndSaveScheduled();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Parsing completed successfully",
                    "currencyGroups", result.size(),
                    "details", result.keySet()
            ));
        } catch (Exception e) {
            log.error("Error during manual parsing", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Parsing failed: " + e.getMessage()
            ));
        }
    }
}