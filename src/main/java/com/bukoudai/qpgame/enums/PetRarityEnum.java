package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 稀有度
 *
 * @author zb
 */
@AllArgsConstructor
@Getter
public enum PetRarityEnum {
  ORDINARY(0, "普通"),
  RARE(1, "稀有"),
  EPIC(2, "史诗"),
  LEGEND(3, "传说");

  private Integer code;
  private String label;


  public static PetRarityEnum parse(Integer type) {
    if (type == null) {
      return null;
    }
    for (PetRarityEnum value : PetRarityEnum.values()) {
      if (value.getCode().equals(type)) {
        return value;
      }
    }
    return null;

  }
}
