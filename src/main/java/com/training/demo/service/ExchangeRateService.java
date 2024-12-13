package com.training.demo.service;

import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.model.request.ForexRequest;
import com.training.demo.model.response.ForexResponse;
import java.util.List;

public interface ExchangeRateService {

  void saveDailyUsdToNtd(List<DailyForexRatesDto> dtos);

  ForexResponse findByDateBetweenAndCurrency(ForexRequest req);
}
