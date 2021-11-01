package com.bukoudai.qpgame.utlis;


import com.bukoudai.qpgame.vo.SendMsgVo;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.apache.commons.lang3.StringUtils;

public class BotUtils {
  public static void sendMsg(Group group, GroupMessageEvent event, SendMsgVo msgVo) {


    if (msgVo != null && StringUtils.isNotBlank(msgVo.getMessage())) {
      String s = msgVo.getMessage();
      boolean doWhile = true;
      boolean needReply = msgVo.getNeedReply();

      if (event == null || event.getSender().getId() == 80000000L) {
        needReply = false;
      }
      while (doWhile) {
        String msg = s;
        if (s.length() > 4999) {
          msg = s.substring(0, 4999);
          s = s.substring(4999);
        } else {
          doWhile = false;
        }
        MessageChainBuilder singleMessages = new MessageChainBuilder();
        if (event != null && needReply) {
          singleMessages.append(new QuoteReply(event.getMessage()));
          needReply = false;
        }
        group.sendMessage(singleMessages.append(msg).build());
      }


    }
  }
}
