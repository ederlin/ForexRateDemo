package com.training.demo.model.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "forexRates")
public class ForexRatesPo {

  @Id
  private String id;
  private LocalDateTime dateTime;
  private String baseCurrency;
  private String quoteCurrency;
  private double rates;

}
