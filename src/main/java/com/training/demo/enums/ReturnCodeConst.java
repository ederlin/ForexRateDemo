package com.training.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCodeConst {

  // S0000 成功
  S0000("0000","成功"),
  // E001 失敗
  E001("E001","日期區間不符");

  private final String code;

  private final String message;

}
