package com.bukoudai.qpgame.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * 命令接口
 */
public interface Command {
    /**
     * 执行
     */
    String execute(GroupMessageEvent event, long botId);
}
