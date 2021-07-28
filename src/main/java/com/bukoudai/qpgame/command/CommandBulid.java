package com.bukoudai.qpgame.command;

import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Service;

/**
 * 更具消息创建不同命令
 */
@Service
@AllArgsConstructor
public class CommandBulid {


    private final DrawCardCommand drawCardCommand;
    private final AddPointCommand addPointCommand;
    private final RollCommand rollCommand;


    public Command bulid(MessageEvent event) {
        MessageChain message = event.getMessage();
        String content = message.contentToString();
        //普通消息
        if (!content.startsWith("/")) {
            return addPointCommand;
        }

        if (content.substring(1).startsWith("抽卡")) {
            return drawCardCommand;
        }
        if (content.substring(1).startsWith("投骰子")) {
            return rollCommand;
        }
        //未识别命令 算作普通消息
        return addPointCommand;
    }
}
