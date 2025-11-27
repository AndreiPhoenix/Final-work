package com.example.currencyparser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "Currency Parser Application is running! Use /api/parser/start to start parsing.";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}