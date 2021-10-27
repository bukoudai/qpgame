package com.bukoudai.qpgame.command.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.service.JokesService;
import com.bukoudai.qpgame.service.UserService;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JokesCommand implements Command {


  private final JokesService jokesService;
  private final UserService userService;


  @Override
  public String execute(MessageEvent event, long botId) {

    long id = event.getSender().getId();
    String nick = event.getSender().getNick();
    String s = event.getMessage().contentToString();
    String s2 = s.replaceFirst(" ", SPLIS_WORDS);
    String[] s1 = s2.split(SPLIS_WORDS);

    if (s1.length > 1) {
      //第一个参数 待添加数据
      String text = s1[1];
      if ("?".equals(text)) {
        return help();
      }
      User user = User.builder().loginNo(id).build();
      QueryWrapper<User> wrapper = new QueryWrapper<>(user);
      User one = userService.getOne(wrapper);
      if (one == null) {
        user.setRole(3);
        user.setNick(nick);
        userService.save(user);
      } else {
        user = one;
      }

      Integer role = user.getRole();
      if (role.compareTo(2) > 0) {
        return "权限不足 请充值!";
      }

      Jokes add = new Jokes();
      add.setText(text);
      add.setType(JokesTypeEnum.DAILY_PROVERB.getCode());
      jokesService.save(add);

      return "存货+1";
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
