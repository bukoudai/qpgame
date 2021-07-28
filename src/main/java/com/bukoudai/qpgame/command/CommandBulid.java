package com.bukoudai.qpgame.command;

import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandBulid {


    private final DrawCardCommand drawCardCommand;
    private final AddPointCommand addPointCommand;
    private final RollCommand rollCommand;


    public Command bulid(MessageEvent event) {
        MessageChain message = event.getMessage();
        String content = message.contentToString();
        //非命令
        if (!content.startsWith("/")) {
            return addPointCommand;
        }

        if (content.substring(1).startsWith("抽卡")) {
            return drawCardCommand;
        }
        if (content.substring(1).startsWith("投骰子")) {
            return rollCommand;
        }

        return addPointCommand;
    }
}
