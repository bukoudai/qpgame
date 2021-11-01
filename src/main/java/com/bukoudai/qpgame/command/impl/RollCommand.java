package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.util.RandomUtil;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RollCommand implements Command {


  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {
    return SendMsgVo.msg(String.valueOf(RandomUtil.randomInt(1010) + 97));


  }

  @Override
  public String help() {
    return "/roll ";
  }
}
