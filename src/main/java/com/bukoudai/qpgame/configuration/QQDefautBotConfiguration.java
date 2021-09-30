package com.bukoudai.qpgame.configuration;

import com.bukoudai.qpgame.msgservice.FriendMessageConsumer;
import com.bukoudai.qpgame.msgservice.GroupMessageEventService;
import com.bukoudai.qpgame.service.BotsService;
import com.bukoudai.qpgame.utlis.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zb
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class QQDefautBotConfiguration {

  private final GroupMessageEventService groupMessageEventService;
  private final FriendMessageConsumer friendMessageConsumer;
  private final BotsService botsService;

  @Bean("myBot")
  public Bot setDefaultBot() {
    //登录用户
    Bot bot = botsService.loginBot();
    if (bot != null) {
      long botId = bot.getId();
      bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, friendMessageConsumer);
      bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, event ->
              BotUtils.sendMsg(event.getGroup(), event, groupMessageEventService.executCommand(event, botId))
      );
      log.info("机器人启动");
    }
    return bot;
  }


}

