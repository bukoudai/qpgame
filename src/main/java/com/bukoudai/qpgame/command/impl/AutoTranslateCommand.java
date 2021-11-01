package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.enums.TransLateLangEnum;
import com.bukoudai.qpgame.service.UserAbilityService;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AutoTranslateCommand implements Command {
  private final UserAbilityService userAbilityService;


  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {
    String s = event.getMessage().contentToString();
    long senderId = event.getSender().getId();


    String s2 = s.replaceFirst(" ", SPLIS_WORDS);

    String[] s1 = s2.split(SPLIS_WORDS);

    String text;

    if (s1.length > 1) {
      //第一个参数 开启关闭命令
      text = s1[1];
      if ("?".equals(text)) {

        return SendMsgVo.msg(help());
      }

      if ("开启".equals(text)) {
        userAbilityService.openOrCloseAutoTranslation(senderId, true);
        return SendMsgVo.msg("自动翻译 开启");
      }
      if ("关闭".equals(text)) {
        userAbilityService.openOrCloseAutoTranslation(senderId, false);
        return SendMsgVo.msg("自动翻译 关闭");
      }
    }


    return SendMsgVo.msg("参数错误 /自动翻译 [开启/关闭]");


  }

  @Override
  public String help() {
    StringBuilder help = new StringBuilder();
    for (TransLateLangEnum value : TransLateLangEnum.values()) {
      help.append(value.getLabel()).append("  ").append(value.getCode()).append("\n");
    }


    return "/自动翻译 [开启/关闭] ";
  }
}
