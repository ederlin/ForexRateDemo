package com.training.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.training.demo.model.response.bean.Currency;
import com.training.demo.model.response.bean.ResHeader;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForexResponse {

  private ResHeader resHeader;
  private List<Currency> currency;

  public ForexResponse(ResHeader resHeader) {
    this.resHeader = resHeader;
  }

}
