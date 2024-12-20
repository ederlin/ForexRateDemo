package com.training.demo.controller;

import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.model.request.ForexRequest;
import com.training.demo.model.response.ForexResponse;
import com.training.demo.service.ExchangeRateService;
import com.training.demo.service.ForeignApiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ForexController {

  private static final String SWAGGER_TAG_NAME = "外幣匯率";

  private final  ExchangeRateService exchangeRateService;

  private final ForeignApiService foreignApiService;

  @PostMapping("/queryForexData")
  @Operation(tags = SWAGGER_TAG_NAME, summary = "匯率歷史資料查詢(美元/台幣)", operationId = "匯率歷史資料查詢(美元/台幣)", description = "匯率歷史資料查詢(美元/台幣)")
  public ForexResponse queryForex(@RequestBody @Valid ForexRequest req) {
    return exchangeRateService.findByDateBetweenAndCurrency(req);
  }

}
