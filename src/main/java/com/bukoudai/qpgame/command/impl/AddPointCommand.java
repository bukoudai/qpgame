package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.UserPoints;
import com.bukoudai.qpgame.entitys.UserTextContentLog;
import com.bukoudai.qpgame.mapper.UserPointsMapper;
import com.bukoudai.qpgame.mapper.UserTextContentLogMapper;
import com.bukoudai.qpgame.service.UserAbilityService;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 添加积分命令
 */
@Service
@AllArgsConstructor
public class AddPointCommand implements Command {


  private final UserPointsMapper userPointsMapper;
  private final UserTextContentLogMapper userTextContentLogMapper;
  private final UserAbilityService UserAbilityService;


  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {
    User sender = event.getSender();
    long senderId = sender.getId();
    String nick = sender.getNick();
    UserPoints userPoints = userPointsMapper.selectOne(new QueryWrapper<>(UserPoints.builder().loginNo(senderId).build()));
    if (userPoints == null) {
      userPointsMapper.insert(UserPoints.builder().loginNo(senderId).points(1).build());
    } else {
      userPoints.setPoints(userPoints.getPoints() + 1);
      userPointsMapper.updateById(userPoints);
    }


    //记录日志
    UserTextContentLog.UserTextContentLogBuilder logBuilder = UserTextContentLog.builder()
            .creatTime(DateUtil.format(new Date(), "HHmmss"))
            .creatDay(DateUtil.format(new Date(), "yyyyMMdd"))
            .textContent(event.getMessage().contentToString())
            .user(String.valueOf(senderId)).nick(nick);
    userTextContentLogMapper.insert(logBuilder.build());
    //判断是否开启实时翻译
    if (UserAbilityService.checkAutoTranslation(senderId)) {

    }


    // 开启实时翻译则翻译
    //未开启 则判断是否非中文

    return null;
  }

  @Override
  public String help() {
    return null;
  }
}
