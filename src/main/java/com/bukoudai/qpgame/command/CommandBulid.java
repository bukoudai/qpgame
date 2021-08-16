package com.bukoudai.qpgame.command;

import com.bukoudai.qpgame.command.impl.*;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import org.springframework.stereotype.Service;

/**
 * 更具消息创建不同命令
 */
@Service
@AllArgsConstructor
public class CommandBulid {
    private final AddPointCommand addPointCommand;
    private final AtCommand atCommand;

    private static final String DRAW_CARD_COMMAND_KEY="/抽卡";
    private final DrawCardCommand drawCardCommand;

    private static final String ROLL_COMMAND_KEY="/roll";
    private final RollCommand rollCommand;

    private static final String FYINFO_API_COMMAND_KEY="/疫情";
    private final FyInfoApiCommand fyInfoApiCommand;

    private static final String USER_PETS_COMMAND_KEY="/pet";
    private final UserPetsCommand userPetsCommand;

    private static final String PK_RPETS_COMMAND_KEY="/pk";
    private final PkPetsCommand pkPetsCommand;


    public Command bulid(MessageEvent event, long botId) {
        MessageChain message = event.getMessage();

        String content = message.contentToString();
        if (content.startsWith(DRAW_CARD_COMMAND_KEY)) {
            return drawCardCommand;
        }
        if (content.startsWith(ROLL_COMMAND_KEY)) {
            return rollCommand;
        }
        if (content.startsWith(FYINFO_API_COMMAND_KEY)) {
            return fyInfoApiCommand;
        }

        if (content.startsWith(USER_PETS_COMMAND_KEY)) {
            return userPetsCommand;
        }

        if (content.startsWith(PK_RPETS_COMMAND_KEY)) {
            return pkPetsCommand;
        }


        for (SingleMessage singleMessage : message) {
            if (singleMessage instanceof At) {
                At at = (At) singleMessage;
                if (at.getTarget() == botId) {

                    return atCommand;
                }
            }
        }

        //未识别命令 算作普通消息
        return addPointCommand;
    }
}
