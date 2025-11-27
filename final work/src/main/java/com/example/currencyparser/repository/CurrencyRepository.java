package com.example.currencyparser.repository;

import com.example.currencyparser.repository.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyRate, Long> {

    List<CurrencyRate> findByCurrencyCodeOrderByTimestampDesc(String currencyCode);

    List<CurrencyRate> findBySourceOrderByTimestampDesc(String source);

    Optional<CurrencyRate> findTopByCurrencyCodeOrderByTimestampDesc(String currencyCode);

    @Query("SELECT cr FROM CurrencyRate cr WHERE cr.currencyCode IN :codes AND cr.timestamp >= :fromDate ORDER BY cr.timestamp DESC")
    List<CurrencyRate> findLatestByCodes(List<String> codes, LocalDateTime fromDate);

    List<CurrencyRate> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime start, LocalDateTime end);
}