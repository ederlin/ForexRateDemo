package com.training.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyForexRatesDto {

  @JsonProperty("Date")
  private String date;

  @JsonProperty("USD/NTD")
  private String usdToNtd;
}