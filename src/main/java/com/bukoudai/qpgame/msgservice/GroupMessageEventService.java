package com.bukoudai.qpgame.msgservice;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.command.CommandBuild;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class GroupMessageEventService {

  private final CommandBuild commandBulid;

  public SendMsgVo executCommand(MessageEvent event, long botId) {
  log.info("event " );
    Command bulid = commandBulid.build(event, botId);
    if (bulid == null) {
      return null;
    }
    return bulid.execute(event, botId);
  }

}
