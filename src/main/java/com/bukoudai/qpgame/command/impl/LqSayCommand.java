package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.configuration.CommandConfig;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.service.JokesService;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LqSayCommand implements Command {


  private final JokesService jokesService;


  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {


    String s = event.getMessage().contentToString();
    String s2 = s.replaceFirst(" ", SPLIS_WORDS);
    String[] s1 = s2.split(SPLIS_WORDS);

    if (s1.length > 1) {
      //第一个参数 待添加数据
      String text = s1[1];
      if ("?".equals(text)) {
        return SendMsgVo.msg(help());
      }


      Jokes add = new Jokes();
      add.setText(text);
      add.setType(JokesTypeEnum.LQSAY.getCode());
      jokesService.save(add);
      return SendMsgVo.msg(CommandConfig.LQSAY_COMMAND_KEY_SUCCESSMSG);

    } else {
      StringBuilder re = new StringBuilder();
      List<Jokes> jokes = jokesService.queryListByType(JokesTypeEnum.LQSAY);
      for (int i = 0; i < jokes.size(); i++) {

        re.append(i).append(":").append(jokes.get(i).getText()).append("\r\n");

      }
      return SendMsgVo.msg(re.toString());

    }


  }

  @Override
  public String help() {
    return CommandConfig.LQSAY_COMMAND_KEY_HELPMSG;
  }
}
