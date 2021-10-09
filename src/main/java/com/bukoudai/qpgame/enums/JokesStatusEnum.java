package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JokesStatusEnum {


  UNUSED(0, "未使用"),
  USED(1, "已使用");

  private Integer code;
  private String label;


  public static JokesStatusEnum parse(Integer type) {
    if (type == null) {
      return null;
    }
    for (JokesStatusEnum value : JokesStatusEnum.values()) {
      if (value.getCode().equals(type)) {
        return value;
      }
    }
    return null;

  }

}
