package com.bukoudai.qpgame.configuration;

import com.bukoudai.qpgame.command.impl.FriendMessageConsumer;
import com.bukoudai.qpgame.msgservice.GroupMessageEventService;
import com.bukoudai.qpgame.service.BotsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class QQDefautBotConfiguration {

    private final GroupMessageEventService groupMessageEventService;
    private final BotsService botsService;
    @Bean("myBot")
    public Bot setDefaultBot() {
        //登录用户
        Bot bot = botsService.loginBot();
        if (bot != null) {
            long botId = bot.getId();
            bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, new FriendMessageConsumer());
            bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, (event) -> {
                String s = groupMessageEventService.executCommand(event, botId);
                if (StringUtils.isNotBlank(s)) {
                    event.getSubject().sendMessage(new MessageChainBuilder()
                            .append(new QuoteReply(event.getMessage()))
                            .append(s)
                            .build()
                    );
                }
            });
            log.info("机器人启动");
        }
        return bot;
    }


}
