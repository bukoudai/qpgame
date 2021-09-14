package com.bukoudai.qpgame.enums;


import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.command.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 指令枚举
 *
 * @author zb
 */
@AllArgsConstructor
@Getter
public enum CommandEnum {
    /**
     * 抽卡
     */
    DRAW_CARD_COMMAND("/抽卡", DrawCardCommand.class, "抽卡"),
    /**
     * 宠物
     */
    USER_PETS_COMMAND("/pet", UserPetsCommand.class, "宠物"),
    /**
     * PK
     */
    PK_PETS_COMMAND("/pk", PkPetsCommand.class, "PK"),
    /**
     * 帮助
     */
    HELP_COMMAND("/help", HelpCommand.class, "帮助"),
    /**
     * roll
     */
    ROLL_COMMAND("/roll", RollCommand.class, "roll"),
    /**
     * 翻译
     */
    TRANSLATE_COMMAND("/翻译", TranslateApiCommand.class, "翻译"),
    /**
     * 疫情信息
     */
    FY_INFO_API_COMMAND("/疫情", FyInfoApiCommand.class, "疫情信息");


    private String key;
    private Class<? extends Command> command;

    private String label;

    public static CommandEnum match(String command) {
        if (command == null) {
            return null;
        }
        for (CommandEnum value : CommandEnum.values()) {
            if (command.startsWith(value.getKey())) {
                return value;
            }
        }

        return null;

    }

}
