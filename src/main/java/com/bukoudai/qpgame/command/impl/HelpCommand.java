package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.enums.CommandEnum;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HelpCommand implements Command {


  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {

    StringBuilder help = new StringBuilder();

    for (CommandEnum value : CommandEnum.values()) {
      help.append(value.getKey()).append(" ").append(value.getLabel()).append("\r\n");

    }


    return SendMsgVo.msg(help.toString());

  }

  @Override
  public String help() {
    return "/help 帮助";
  }
}
