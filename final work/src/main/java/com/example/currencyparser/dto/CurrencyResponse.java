package com.example.currencyparser.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CurrencyResponse {
    private String currencyName;
    private String currencyCode;
    private Double rateToRub;
    private Double rateToUsd;
    private Double change24h;
    private String source;
    private LocalDateTime timestamp;
}