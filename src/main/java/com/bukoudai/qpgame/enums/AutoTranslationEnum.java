package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否开启自动翻译
 */
@AllArgsConstructor
@Getter
public enum AutoTranslationEnum {


  UNUSED(0, "关闭"),
  USED(1, "开启");

  private Integer code;
  private String label;


  public static AutoTranslationEnum parse(Integer type) {
    if (type == null) {
      return null;
    }
    for (AutoTranslationEnum value : AutoTranslationEnum.values()) {
      if (value.getCode().equals(type)) {
        return value;
      }
    }
    return null;

  }
}
