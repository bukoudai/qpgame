package com.bukoudai.qpgame.msgservice;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.command.CommandBuild;
import com.bukoudai.qpgame.entitys.UserTextContentPic;
import com.bukoudai.qpgame.service.UserTextContentPicService;
import com.bukoudai.qpgame.thirdapi.cosapi.CosApiProcess;
import com.bukoudai.qpgame.vo.SendMsgVo;
import com.qcloud.cos.model.UploadResult;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

@Service
@Slf4j
public class FriendMessageConsumer implements Consumer<FriendMessageEvent> {

  @Value("${bot.your_qq_number}")
  private String yourQQNumber;
  @Autowired
  private CommandBuild commandBulid;


  @Override
  public void accept(FriendMessageEvent event) {

    if (Long.parseLong(yourQQNumber) == (event.getSender().getId())) {

      String msg = "";
      SendMsgVo msgVo = new SendMsgVo("", true);
      Command bulid = commandBulid.build(event, 1);
      if (bulid != null) {
        msgVo = bulid.execute(event, 1);
        if (msgVo == null) {
            msgVo = new SendMsgVo(null, true);
        }
        msg = msgVo.getMessage();
      }
      MessageChainBuilder append = new MessageChainBuilder()
              .append(msg);

      if (msgVo.getNeedReply()) {
        append.append(new QuoteReply(event.getMessage()));
      }
      if (msgVo.getMessage() != null) {
        event.getSubject().sendMessage(append.build());
      }


    }
  }
}
