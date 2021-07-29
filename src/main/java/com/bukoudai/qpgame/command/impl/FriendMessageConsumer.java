package com.bukoudai.qpgame.command.impl;

import cn.hutool.json.JSONObject;
import com.bukoudai.qpgame.thirdapi.FyInfo;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.function.Consumer;

public class FriendMessageConsumer implements Consumer<FriendMessageEvent> {


    @Override
    public void accept(FriendMessageEvent event) {
        long yourQQNumber = 1111111;
        if (event.getSender().getId() == yourQQNumber) {
            String msg = "";
            if (event.getMessage().contentToString().startsWith("/疫情")) {
                StringBuilder msgB = new StringBuilder("今日疫情信息:\r\n");

                JSONObject fyCityDtail = FyInfo.getFyCityDtail("四川省", "成都");
                msgB.append(fyCityDtail == null ? "" : fyCityDtail.toStringPretty());

                msg = msgB.toString();
            }


            event.getSubject().sendMessage(new MessageChainBuilder()
                    .append(new QuoteReply(event.getMessage()))
                    .append(msg)
                    .build()
            );
        }
    }
}
