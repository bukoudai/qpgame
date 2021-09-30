package com.bukoudai.qpgame.command;

import com.bukoudai.qpgame.command.impl.AddPointCommand;
import com.bukoudai.qpgame.command.impl.AtCommand;
import com.bukoudai.qpgame.enums.CommandEnum;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 更具消息创建不同命令
 *
 * @author zb
 */
@Service
@AllArgsConstructor
public class CommandBuild {
  private final AddPointCommand addPointCommand;
  private final AtCommand atCommand;


  private ApplicationContext applicationContext;


  public Command build(MessageEvent event, long botId) {
    MessageChain message = event.getMessage();

    String content = message.contentToString();

    CommandEnum match = CommandEnum.match(content);
    if (match != null) {
      return applicationContext.getBean(match.getCommand());
    }

    for (SingleMessage singleMessage : message) {
      if (singleMessage instanceof At) {
        At at = (At) singleMessage;
        if (at.getTarget() == botId) {
          return atCommand;
        }
      }
    }

    //未识别命令 算作普通消息
    return addPointCommand;
  }
}
