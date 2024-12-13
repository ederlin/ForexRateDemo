package com.training.demo.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ForexRequest {

  @JsonProperty("startDate")
  @Schema(title = "開始日期", example = "2024/01/01")
  private String startDate;

  @JsonProperty("endDate")
  @Schema(title = "結束日期", example = "2025/01/01")
  private String endDate;

  @JsonProperty("currency")
  @Schema(title = "幣別", example = "USD")
  private String currency;
}