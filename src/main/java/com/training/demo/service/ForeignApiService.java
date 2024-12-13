package com.training.demo.service;

import com.training.demo.model.dto.DailyForexRatesDto;
import java.util.List;

public interface ForeignApiService {

  List<DailyForexRatesDto> fetchDailyForexRates();
}
