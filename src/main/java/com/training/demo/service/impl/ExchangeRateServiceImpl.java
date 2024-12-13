package com.training.demo.service.impl;

import com.training.demo.enums.ReturnCodeConst;
import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.model.entity.ForexRatesPo;
import com.training.demo.model.request.ForexRequest;
import com.training.demo.model.response.ForexResponse;
import com.training.demo.model.response.bean.Currency;
import com.training.demo.model.response.bean.ResHeader;
import com.training.demo.repository.ForexRateRepository;
import com.training.demo.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl  implements ExchangeRateService {

  private final ForexRateRepository forexRateRepository;

  /**
   * 將每日的美元/台幣欄位(USD/NTD)資料與日期(yyyy-MM-dd HH:mm:ss)儲存至DB
   * @param dtos
   */
  @Override
  public void saveDailyUsdToNtd(List<DailyForexRatesDto> dtos) {
      List<ForexRatesPo>  forexRatesList = dtos
          .stream()
          .map(this::convertDailyForexRatesDtoToForexRates)
          .collect(Collectors.toList());

    // 先判斷第一筆資料在資料庫是否存在
     Optional<ForexRatesPo> firstData = forexRateRepository.findByDateTime(forexRatesList.get(0).getDateTime());
     if(!firstData.isPresent()) {
        // 第一次存 DB
        forexRateRepository.saveAll(forexRatesList);
       log.info("------ first save end ------");
     } else {
        // 判斷最後一筆資料在資料庫是否存在，碰到假日資料不會更新
       Optional<ForexRatesPo> lastData = forexRateRepository.findByDateTime(forexRatesList.get(forexRatesList.size() - 1).getDateTime());
       if(!lastData.isPresent()) {
         forexRateRepository.save(forexRatesList.get(forexRatesList.size() - 1));
         log.info("------ daily save end ------");
       }
     }
  }

  /**
   * 取出日期區間內美元/台幣的歷史資料
   * @param req
   * @return
   */
  @Override
  public ForexResponse findByDateBetweenAndCurrency(ForexRequest req) {
    ForexResponse res = new ForexResponse();
    List<Currency>  currencies = new ArrayList<>();

    try {
      LocalDate startDate = LocalDate.parse(req.getStartDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
      LocalDate endDate = LocalDate.parse(req.getEndDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

      // 檢查日期區間是否有效
      if (!isValidDateRange(startDate, endDate)) {
        return new ForexResponse(new ResHeader(ReturnCodeConst.E001));
      }

      List<ForexRatesPo> list = forexRateRepository.findByDateTimeBetweenAndQuoteCurrency(startDate, endDate, req.getCurrency().toUpperCase());

      if(!list.isEmpty()) {
        list.forEach(data -> {
          Currency currency = Currency.builder()
              .date(data.getDateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
              .usd(String.valueOf(data.getRates()))
              .build();
          currencies.add(currency);
        });
      }

      res.setResHeader(new ResHeader(ReturnCodeConst.S0000));
      res.setCurrency(currencies);
    } catch (Exception e) {
      return new ForexResponse(new ResHeader(ReturnCodeConst.E001));
    }

    return res;
  }

  public ForexRatesPo convertDailyForexRatesDtoToForexRates(DailyForexRatesDto dto) {
    return ForexRatesPo.builder()
        .dateTime(covertStringToLocalDateTime(dto.getDate()).plusHours(8)) // 存 MongoDB 會將其轉換為 UTC 時間，所以加 8 小時
        .baseCurrency("NTD")
        .quoteCurrency("USD")
        .rates(Double.parseDouble(dto.getUsdToNtd()))
        .build();
  }

  public boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
    LocalDate oneYearAgo = LocalDate.now().minusYears(1);
    LocalDate yesterday = LocalDate.now().minusDays(1);
    return !startDate.isBefore(oneYearAgo) && !endDate.isAfter(yesterday) && !startDate.isAfter(endDate);
  }

  private LocalDateTime covertStringToLocalDateTime(String sDateTime) {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    LocalDate parsedDate = LocalDate.parse(sDateTime, inputFormatter);
    return parsedDate.atStartOfDay();
  }

}
