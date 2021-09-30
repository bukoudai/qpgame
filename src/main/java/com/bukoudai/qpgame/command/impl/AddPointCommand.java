package com.bukoudai.qpgame.command.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.UserPoints;
import com.bukoudai.qpgame.mapper.UserPointsMapper;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

/**
 * 添加积分命令
 */
@Service
@AllArgsConstructor
public class AddPointCommand implements Command {


  private final UserPointsMapper userPointsMapper;


  @Override
  public String execute(MessageEvent event, long botId) {

    long senderId = event.getSender().getId();
    UserPoints userPoints = userPointsMapper.selectOne(new QueryWrapper<>(UserPoints.builder().loginNo(senderId).build()));
    if (userPoints == null) {
      userPointsMapper.insert(UserPoints.builder().loginNo(senderId).points(1).build());
    } else {
      userPoints.setPoints(userPoints.getPoints() + 1);
      userPointsMapper.updateById(userPoints);
    }
    return null;
  }

  @Override
  public String help() {
    return null;
  }
}
