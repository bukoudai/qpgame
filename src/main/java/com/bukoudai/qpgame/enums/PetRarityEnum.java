package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 稀有度
 * @author zb
 */
@AllArgsConstructor
@Getter
public enum PetRarityEnum {
    ordinary(0, "普通"),
    rare(1, "稀有"),
    epic(2, "史诗"),
    legend(3, "传说");

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
