package com.bukoudai.qpgame.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.Bots;
import com.bukoudai.qpgame.mapper.BotsMapper;
import com.bukoudai.qpgame.service.BotsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;

import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.stereotype.Service;
import xyz.cssxsh.mirai.tool.FixProtocolVersion;

import java.util.Map;
@Slf4j
@Service
@AllArgsConstructor
public class BotsServiceImpl extends ServiceImpl<BotsMapper, Bots> implements BotsService, IService<Bots> {
  @Override
  public Bots getDefaultBot() {
    return baseMapper.selectOne(null);
  }
  // 升级协议版本
  public static void update2() {
    FixProtocolVersion.update();
  }
  // 同步协议版本
  public static void sync2() {
    FixProtocolVersion.sync(BotConfiguration.MiraiProtocol.ANDROID_PAD);
  }
  // 获取协议版本信息 你可以用这个来检查update是否正常工作
  public static Map<BotConfiguration.MiraiProtocol, String> info2() {
    return FixProtocolVersion.info();
  }
  @Override
  public Bot loginBot(Bots bots) {
    log.info("升级协议版本");
    update2();
    log.info("同步协议版本");
    sync2();

    Map<BotConfiguration.MiraiProtocol, String> miraiProtocolStringMap = info2();
    log.info("协议版本信息:{}",miraiProtocolStringMap);

    String deviceJson = bots.getDeviceJson();
    String loginPassword = bots.getLoginPassword();
    Long loginNo = bots.getLoginNo();
    BotConfiguration botConfiguration = new BotConfiguration();
    if (deviceJson != null) {
      botConfiguration.loadDeviceInfoJson(deviceJson);
    }

    botConfiguration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);

    Bot bot = BotFactory.INSTANCE.newBot(loginNo, loginPassword, botConfiguration);

    bot.login();
    return bot;
  }

  @Override
  public Bot loginBot() {
    return loginBot(getDefaultBot());
  }
}
