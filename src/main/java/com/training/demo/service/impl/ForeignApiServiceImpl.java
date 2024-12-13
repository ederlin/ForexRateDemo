package com.training.demo.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.demo.constant.OpenAPIConst;
import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.service.ForeignApiService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class ForeignApiServiceImpl implements ForeignApiService {

  private final RestTemplate restTemplate;

  @Override
  public List<DailyForexRatesDto> fetchDailyForexRates() {
    ObjectMapper mapper = new ObjectMapper();
    List<DailyForexRatesDto> filteredList = new ArrayList<>();

    try {
      //  取得每日外幣匯率
      String jsonString = restTemplate
          .getForObject(OpenAPIConst.API_BASE_URL + OpenAPIConst.DAILY_FOREIGN_EXCHANGE_RATES, String.class);
      log.info("DailyForeignExchangeRates jsonString: {} " , jsonString);
      List<Map<String, String>> dtos = mapper.readValue(jsonString, new TypeReference<>() {});

      // 篩選出USD/NTD
      for (Map<String, String> dto : dtos) {
        DailyForexRatesDto filteredDto = new DailyForexRatesDto();
        filteredDto.setDate(dto.get("Date"));
        filteredDto.setUsdToNtd(dto.get("USD/NTD"));
        filteredList.add(filteredDto);
      }
    } catch (Exception e) {
      log.error("fetchDailyForexRates error: {} " , e.getMessage(), e);
    }
    return filteredList;
  }

}
