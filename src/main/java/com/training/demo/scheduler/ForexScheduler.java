package com.training.demo.scheduler;

import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.service.ExchangeRateService;
import com.training.demo.service.ForeignApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ForexScheduler {

  private final ForeignApiService foreignApiService;
  private final ExchangeRateService exchangeRateService;

  /**
   * 每日 18:00 執行
   * 取得外幣(USD/NTD)參考匯率資料並存入資料庫
   */
  @Scheduled(cron = "0 0 18 * * ?")
   public void fetchForexRatesSchedule() {
    log.info("------ ForexScheduler.fetchForexRatesSchedule start ------");
    try {
        // 獲取每日外幣參考匯率
        List<DailyForexRatesDto> dailyForexRatesDtos = foreignApiService.fetchDailyForexRates();
        log.info("------ dailyForexRates 共有 {} 筆 ------" , dailyForexRatesDtos.size());

        if(!dailyForexRatesDtos.isEmpty()) {
            exchangeRateService.saveDailyUsdToNtd(dailyForexRatesDtos);
        }
    } catch (Exception e) {
        log.error("fetchForexRatesSchedule error: {} " , e.getMessage(), e);
    }
    log.info("------ ForexScheduler.fetchForexRatesSchedule end ------");
  }
}
