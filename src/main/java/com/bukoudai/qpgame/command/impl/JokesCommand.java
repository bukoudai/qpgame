package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.service.JokesService;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JokesCommand implements Command {


  private final JokesService jokesService;


  @Override
  public String execute(MessageEvent event, long botId) {


    String s = event.getMessage().contentToString();
    String s2 = s.replaceFirst(" ", SPLIS_WORDS);
    String[] s1 = s2.split(SPLIS_WORDS);

    if (s1.length > 1) {
      //第一个参数 待添加数据
      String text = s1[1];
      if ("?".equals(text)) {
        return help();
      }
      Jokes add = new Jokes();
      add.setText(text);
      add.setType(JokesTypeEnum.DAILY_PROVERB.getCode());
      jokesService.save(add);

      return "success";
    } else {
      Jokes jokes = jokesService.randomOneByType(JokesTypeEnum.DAILY_PROVERB, false);
      return jokes.getText();
    }


  }

  @Override
  public String help() {
    return "/记录 查看随机一条 \r\n /记录 文本 添加一条";
  }
}
