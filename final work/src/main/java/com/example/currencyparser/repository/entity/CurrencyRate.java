package com.example.currencyparser.repository.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private Double rateToRub;

    private Double rateToUsd;

    @Column(nullable = false)
    private Double change24h;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Конструкторы
    public CurrencyRate() {}

    public CurrencyRate(String currencyName, String currencyCode, Double rateToRub,
                        Double rateToUsd, Double change24h, String source) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.rateToRub = rateToRub;
        this.rateToUsd = rateToUsd;
        this.change24h = change24h;
        this.source = source;
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Double getRateToRub() {
        return rateToRub;
    }

    public Double getRateToUsd() {
        return rateToUsd;
    }

    public Double getChange24h() {
        return change24h;
    }

    public String getSource() {
        return source;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setRateToRub(Double rateToRub) {
        this.rateToRub = rateToRub;
    }

    public void setRateToUsd(Double rateToUsd) {
        this.rateToUsd = rateToUsd;
    }

    public void setChange24h(Double change24h) {
        this.change24h = change24h;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // toString для удобства отладки
    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", rateToRub=" + rateToRub +
                ", rateToUsd=" + rateToUsd +
                ", change24h=" + change24h +
                ", source='" + source + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    // equals и hashCode для корректной работы с коллекциями
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (!id.equals(that.id)) return false;
        if (!currencyName.equals(that.currencyName)) return false;
        if (!currencyCode.equals(that.currencyCode)) return false;
        if (!rateToRub.equals(that.rateToRub)) return false;
        if (rateToUsd != null ? !rateToUsd.equals(that.rateToUsd) : that.rateToUsd != null) return false;
        if (!change24h.equals(that.change24h)) return false;
        if (!source.equals(that.source)) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + currencyName.hashCode();
        result = 31 * result + currencyCode.hashCode();
        result = 31 * result + rateToRub.hashCode();
        result = 31 * result + (rateToUsd != null ? rateToUsd.hashCode() : 0);
        result = 31 * result + change24h.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}