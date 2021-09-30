package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.util.RandomUtil;
import com.bukoudai.qpgame.command.Command;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtCommand implements Command {
  private static final String[] WORD_LSIT = new String[]{"翻滚吧牛宝宝", "呵呵", "略略略", "凸 -.- 凸", "我是你爸爸"};

  @Override
  public String execute(MessageEvent event, long botId) {
    int length = WORD_LSIT.length;
    int i = RandomUtil.randomInt(length);
    return WORD_LSIT[i];

  }

  @Override
  public String help() {
    return null;
  }
}
