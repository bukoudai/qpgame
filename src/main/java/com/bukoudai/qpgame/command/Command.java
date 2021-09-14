package com.bukoudai.qpgame.command;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 命令接口
 */
public interface Command {

    String SPLIS_WORDS = "9s8j28x91js0ckx0e78vds";

    /**
     * 执行
     */
    String execute(MessageEvent event, long botId);


    String help();
}
