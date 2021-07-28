package com.bukoudai.qpgame.controller;

import com.bukoudai.qpgame.command.FriendMessageConsumer;
import com.bukoudai.qpgame.msgservice.GroupMessageEventService;
import com.bukoudai.qpgame.service.BotsService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "qq机器人"
)
@RestController
@RequestMapping("/bot")
@AllArgsConstructor
public class QQbotController {


    private final GroupMessageEventService groupMessageEventService;
    private final BotsService botsService;

    @PostMapping("/test")
    public void test() {

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

        }

    }


}

