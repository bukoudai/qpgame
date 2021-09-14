package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransLateLangEnum {


    en("en", "英语"),
    zh("zh", "简体中文"),
    ja("ja", "日语"),
    ko("ko", "韩语");

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
