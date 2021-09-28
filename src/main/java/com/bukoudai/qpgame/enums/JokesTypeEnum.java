package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JokesTypeEnum {


    DAILY_PROVERB(0, "每日小贴士"),
    STORY(1, "故事"),
    DIALOGUE(2, "对话");

    private Integer code;
    private String label;


    public static JokesTypeEnum parse(Integer type) {
        if (type == null) {
            return null;
        }
        for (JokesTypeEnum value : JokesTypeEnum.values()) {
            if (value.getCode().equals(type)) {
                return value;
            }
        }
        return null;

    }

}
