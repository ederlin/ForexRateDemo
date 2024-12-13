package com.training.demo.scheduler;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.service.ExchangeRateService;
import com.training.demo.service.ForeignApiService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ForexSchedulerTest {

  @Mock
  private  ForeignApiService foreignApiService;
  @Mock
  private  ExchangeRateService exchangeRateService;
  @InjectMocks
  private ForexScheduler forexScheduler;

  @Test
  void fetchForexRatesScheduleTest() {
    List<DailyForexRatesDto> dailyForexRatesDtos = new ArrayList<>();
    DailyForexRatesDto dailyForexRatesDto = new DailyForexRatesDto();
    dailyForexRatesDto.setDate("20241212");
    dailyForexRatesDto.setUsdToNtd("28.5");
    DailyForexRatesDto dailyForexRatesDto1 = new DailyForexRatesDto();
    dailyForexRatesDto1.setDate("20241213");
    dailyForexRatesDto1.setUsdToNtd("29.5");
    dailyForexRatesDtos.add(dailyForexRatesDto);
    dailyForexRatesDtos.add(dailyForexRatesDto1);

    when(foreignApiService.fetchDailyForexRates()).thenReturn(dailyForexRatesDtos);

    forexScheduler.fetchForexRatesSchedule();

    verify(exchangeRateService, times(1)).saveDailyUsdToNtd(dailyForexRatesDtos);

  }
}
