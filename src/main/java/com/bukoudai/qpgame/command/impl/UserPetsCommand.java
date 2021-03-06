package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.service.UserPetsService;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserPetsCommand implements Command {


  private final UserPetsService userPetsService;
  private static final String LIST_KEY = "-l";
  private static final String MAIN_KEY = "-m";
  private static final String DESTROY_KEY = "-d";


  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {

    String s = event.getMessage().contentToString();
    String s2 = s.replaceFirst(" ", SPLIS_WORDS);

    String[] s1 = s2.split(SPLIS_WORDS);
    long senderId = event.getSender().getId();
    String key1 = "";
    if (s1.length > 1) {
      key1 = s1[1];

    }
    if (LIST_KEY.equals(key1)) {
      List<UserPets> userPets = userPetsService.queryUserPets(User.builder().loginNo(senderId).build());

      return SendMsgVo.msg(userPets.toString());
    } else if (MAIN_KEY.equals(key1)) {
      String key2;
      if (s1.length > 2) {
        key2 = s1[2];
        userPetsService.setMainPets(String.valueOf(senderId), key2);

        return SendMsgVo.msg("设置出战宠物可能成功了 请使用 /pet -m 自己查询结果 ");

      } else {
        UserPets re = userPetsService.getMainPet(String.valueOf(senderId));

        return SendMsgVo.msg(re == null ? "未设置主要宠物" : re.toString());
      }
    } else if (DESTROY_KEY.equals(key1)) {
      String key2;
      if (s1.length > 2) {
        key2 = s1[2];
        userPetsService.destroyUserPets(String.valueOf(senderId), key2);

        return SendMsgVo.msg("成功");
      } else {
        userPetsService.destroyUserPets(String.valueOf(senderId));
        return SendMsgVo.msg("成功");
      }
    }
    return SendMsgVo.msg("指令错误 现有指令" + help());

  }

  @Override
  public String help() {
    return "/pet -l 查看所有宠物 \n" +
            "/pet -m 查看出战宠物 \n" +
            "/pet -m [id] 设置出战宠物 id为宠物id   \n" +
            "/pet -d [id] 销毁指定宠物 id为宠物id   \n" +
            "/pet -d 销毁除出战宠物外其他宠物 \n";
  }
}
