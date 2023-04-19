package com.bukoudai.qpgame.enums;


import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.command.impl.*;
import com.bukoudai.qpgame.configuration.CommandConfig;
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
   * 自动翻译
   */
  AUTO_TRANSLATE_COMMAND("/自动翻译", AutoTranslateCommand.class, "自动翻译"),
  /**
   * 记录
   */
  JOKES_COMMAND("/记录", JokesCommand.class, "记录"),
  LQSAY_COMMAND(CommandConfig.LQSAY_COMMAND_KEY, LqSayCommand.class, CommandConfig.LQSAY_COMMAND_KEY_LABEL),
  /**
   * 疫情信息
   */
  FY_INFO_API_COMMAND("/疫情", FyInfoApiCommand.class, "疫情信息")

  ;


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
      if (command.startsWith("[不支持的消息#")) {
        int i = command.indexOf("]");
        if (i > -1) {
          command = command.substring(i + 1);
          if ((command.startsWith(value.getKey()))) {
            return value;
          }
        }
      }
    }

    return null;

  }

}
