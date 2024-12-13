package com.training.demo.service;

import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.model.entity.ForexRatesPo;
import com.training.demo.model.request.ForexRequest;
import com.training.demo.model.response.ForexResponse;
import com.training.demo.repository.ForexRateRepository;
import com.training.demo.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {
  
    @Mock
    private ForexRateRepository forexRateRepository;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Test
    public void saveDailyUsdToNtdTest() {
        List<DailyForexRatesDto> dailyForexRatesDtos = new ArrayList<>();
        DailyForexRatesDto dailyForexRatesDto = new DailyForexRatesDto();
        dailyForexRatesDto.setDate("20241212");
        dailyForexRatesDto.setUsdToNtd("32.44");
        DailyForexRatesDto dailyForexRatesDto1 = new DailyForexRatesDto();
        dailyForexRatesDto1.setDate("20241213");
        dailyForexRatesDto1.setUsdToNtd("32.51");
        dailyForexRatesDtos.add(dailyForexRatesDto);
        dailyForexRatesDtos.add(dailyForexRatesDto1);

        List<ForexRatesPo>  forexRatesList = new ArrayList<>();
        ForexRatesPo forexRates = new ForexRatesPo();
        forexRates.setBaseCurrency("NTD");
        forexRates.setQuoteCurrency("USD");
        forexRates.setRates(32.44);
        forexRates.setDateTime(LocalDateTime.of(2024, 12, 12, 8, 0,0));
        ForexRatesPo forexRates1 = new ForexRatesPo();
        forexRates1.setBaseCurrency("NTD");
        forexRates1.setQuoteCurrency("USD");
        forexRates1.setRates(32.51);
        forexRates1.setDateTime(LocalDateTime.of(2024, 12, 13, 8, 0,0));
        forexRatesList.add(forexRates);
        forexRatesList.add(forexRates1);

        when(forexRateRepository.findByDateTime(forexRatesList.get(0).getDateTime()))
                .thenReturn(Optional.of(forexRates));

        exchangeRateService.saveDailyUsdToNtd(dailyForexRatesDtos);

        verify(forexRateRepository).save(argThat(data ->
                data.getQuoteCurrency().equals("USD") &&
                        data.getBaseCurrency().equals("NTD") &&
                        data.getRates() == 32.51
        ));
    }

    @Test
    public void findByDateBetweenAndCurrencySuccessTest() {
        ForexRequest req = new ForexRequest();
        req.setStartDate("2024/01/01");
        req.setEndDate("2024/02/29");
        req.setCurrency("usd");

        when(forexRateRepository.findByDateTimeBetweenAndQuoteCurrency(any(), any(), eq("USD")))
            .thenReturn(List.of());

        ForexResponse res = exchangeRateService.findByDateBetweenAndCurrency(req);
        
        assertEquals("0000", res.getResHeader().getCode());
        assertEquals("成功", res.getResHeader().getMessage());
    }
}
