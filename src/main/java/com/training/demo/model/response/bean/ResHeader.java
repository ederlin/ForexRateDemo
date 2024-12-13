package com.training.demo.model.response.bean;

import com.training.demo.enums.ReturnCodeConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResHeader {

  private String code;
  private String message;

  public ResHeader(ReturnCodeConst returnCodeConst) {
    this.code = returnCodeConst.getCode();
    this.message = returnCodeConst.getMessage();
  }
}