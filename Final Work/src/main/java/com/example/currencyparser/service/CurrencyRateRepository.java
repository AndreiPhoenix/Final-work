package com.example.currencyparser.service;

import com.example.currencyparser.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    List<CurrencyRate> findBySource(String source);

    List<CurrencyRate> findByCurrencyCode(String currencyCode);

    @Query("SELECT cr FROM CurrencyRate cr WHERE cr.timestamp = (SELECT MAX(cr2.timestamp) FROM CurrencyRate cr2 WHERE cr2.currencyCode = cr.currencyCode AND cr2.source = cr.source)")
    List<CurrencyRate> findLatestRates();

    List<CurrencyRate> findByBaseCurrency(String baseCurrency);
}