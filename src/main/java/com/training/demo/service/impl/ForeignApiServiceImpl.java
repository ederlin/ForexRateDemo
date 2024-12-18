package com.training.demo.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.demo.mapper.DailyForexRatesMapper;
import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.service.ForeignApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ForeignApiServiceImpl implements ForeignApiService {

  @Value("${open.api.daily.foreign.exchange.rates}")
  private String foreignExchangeRatesUrl;

  private final RestTemplate restTemplate;
  private final DailyForexRatesMapper dailyForexRatesMapper ;

  @Override
  public List<DailyForexRatesDto> fetchDailyForexRates() {
    List<DailyForexRatesDto> dailyForexRatesDtoList = new ArrayList<>();

    try {
      //  取得每日外幣匯率
      String jsonString = restTemplate.getForObject(foreignExchangeRatesUrl, String.class);
      log.info("DailyForeignExchangeRates jsonString: {} " , jsonString);

      ObjectMapper mapper = new ObjectMapper();
      List<Map<String, String>> dtos = mapper.readValue(jsonString, new TypeReference<>() {});
      dailyForexRatesDtoList = dailyForexRatesMapper.jsonMapListToDtoList(dtos);
    } catch (Exception e) {
      log.error("fetchDailyForexRates error: {} " , e.getMessage(), e);
    }
    return dailyForexRatesDtoList;
  }

}
