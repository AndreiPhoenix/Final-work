package com.example.currencyparser.service;

import com.example.currencyparser.repository.CurrencyRepository;
import com.example.currencyparser.repository.entity.CurrencyRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void testGetAllRates() {
        // given
        CurrencyRate rate1 = new CurrencyRate("US Dollar", "USD", 90.5, 1.0, 0.5, "TEST");
        CurrencyRate rate2 = new CurrencyRate("Euro", "EUR", 98.2, 1.08, -0.3, "TEST");

        when(currencyRepository.findAll()).thenReturn(Arrays.asList(rate1, rate2));

        // when
        List<CurrencyRate> result = currencyService.getAllRates(null, null);

        // then
        assertEquals(2, result.size());
        verify(currencyRepository, times(1)).findAll();
    }
}