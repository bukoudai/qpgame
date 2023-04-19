package com.bukoudai.qpgame.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.Bots;
import com.bukoudai.qpgame.mapper.BotsMapper;
import com.bukoudai.qpgame.service.BotsService;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;

import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotsServiceImpl extends ServiceImpl<BotsMapper, Bots> implements BotsService, IService<Bots> {
  @Override
  public Bots getDefaultBot() {
    return baseMapper.selectOne(null);
  }

  @Override
  public Bot loginBot(Bots bots) {


    String deviceJson = bots.getDeviceJson();
    String loginPassword = bots.getLoginPassword();
    Long loginNo = bots.getLoginNo();
    BotConfiguration botConfiguration = new BotConfiguration();
    if (deviceJson != null) {
      botConfiguration.loadDeviceInfoJson(deviceJson);
    }

    botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.MACOS);

    Bot bot = BotFactory.INSTANCE.newBot(loginNo, loginPassword, botConfiguration);

    bot.login();
    return bot;
  }

  @Override
  public Bot loginBot() {
    return loginBot(getDefaultBot());
  }
}
