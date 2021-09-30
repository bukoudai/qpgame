package com.bukoudai.qpgame.msgservice;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.command.CommandBuild;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupMessageEventService {

  private final CommandBuild commandBulid;

  public String executCommand(MessageEvent event, long botId) {

    Command bulid = commandBulid.build(event, botId);
    if (bulid == null) {
      return null;
    }
    return bulid.execute(event, botId);
  }

}
