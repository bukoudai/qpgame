package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransLateLangEnum {


  EN("en", "英语"),
  ZH("zh", "简体中文"),
  JA("ja", "日语"),
  KO("ko", "韩语");

  private String code;
  private String label;


  public static TransLateLangEnum parse(String type) {
    if (type == null) {
      return null;
    }
    for (TransLateLangEnum value : TransLateLangEnum.values()) {
      if (value.getCode().equals(type)) {
        return value;
      }
    }
    return null;

  }

}
