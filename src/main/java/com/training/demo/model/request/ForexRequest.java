package com.training.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.training.demo.annotation.DateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ForexRequest {

  @JsonProperty("startDate")
  @NotBlank(message = "開始日期不能為空")
  @DateTime(pattern = "yyyy/MM/dd")
  @Schema(title = "開始日期", example = "2024/01/01")
  private String startDate;

  @JsonProperty("endDate")
  @NotBlank(message = "結束日期不能為空")
  @DateTime(pattern = "yyyy/MM/dd")
  @Schema(title = "結束日期", example = "2024/12/01")
  private String endDate;

  @JsonProperty("currency")
  @NotBlank(message = "幣別不能為空")
  @Size(min = 3, max = 3, message = "幣別必須為 3 個字母")
  @Schema(title = "幣別", example = "USD")
  private String currency;
}