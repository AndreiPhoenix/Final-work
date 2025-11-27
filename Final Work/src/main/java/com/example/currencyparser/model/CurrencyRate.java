package com.example.currencyparser.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency_rates")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currencyName;

    @Column(nullable = false)
    private String currencyCode;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal rate;

    @Column(nullable = false)
    private String baseCurrency;

    @Column(precision = 10, scale = 4)
    private BigDecimal change24h;

    private String source;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public CurrencyRate() {}

    public CurrencyRate(String currencyName, String currencyCode, BigDecimal rate,
                        String baseCurrency, BigDecimal change24h, String source) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.rate = rate;
        this.baseCurrency = baseCurrency;
        this.change24h = change24h;
        this.source = source;
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCurrencyName() { return currencyName; }
    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }

    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }

    public BigDecimal getChange24h() { return change24h; }
    public void setChange24h(BigDecimal change24h) { this.change24h = change24h; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", rate=" + rate +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", change24h=" + change24h +
                ", source='" + source + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}