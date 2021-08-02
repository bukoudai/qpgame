package com.bukoudai.qpgame.utlis;


import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.apache.commons.lang3.StringUtils;

public class BotUtils {
    public static void sendMsg(Group group, GroupMessageEvent event, String s) {


        if (StringUtils.isNotBlank(s)) {

            boolean doWhile = true;
            boolean needReply = true;
            while (doWhile) {
                String msg = s;
                if (s.length() > 4999) {
                    msg = s.substring(0, 4999);
                    s = s.substring(4999);
                } else {
                    doWhile = false;
                }
                MessageChainBuilder singleMessages = new MessageChainBuilder().append(msg);
                if (event != null && needReply) {
                    singleMessages.append(new QuoteReply(event.getMessage()));
                    needReply = false;
                }
                group.sendMessage(singleMessages.build());
            }


        }
    }
}
