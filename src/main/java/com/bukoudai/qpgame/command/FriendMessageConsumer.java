package com.bukoudai.qpgame.command;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.function.Consumer;

public class FriendMessageConsumer implements Consumer<FriendMessageEvent> {


    @Override
    public void accept(FriendMessageEvent event) {
        long yourQQNumber = 1111111;
        if (event.getSender().getId() == yourQQNumber) {
            event.getSubject().sendMessage(new MessageChainBuilder()
                    .append(new QuoteReply(event.getMessage()))
                    .append("Hi, you just said: '")
                    .append(event.getMessage())
                    .append("'")
                    .build()
            );
        }
    }
}
