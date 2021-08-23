package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.util.RandomUtil;
import com.bukoudai.qpgame.command.Command;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtCommand implements Command {
    private final static String[] WORD_LSIT = new String[]{"翻滚吧牛宝宝", "呵呵", "略略略","凸 -.- 凸","我是你爸爸"};

    @Override
    public String execute(GroupMessageEvent event, long botId) {
        int length = WORD_LSIT.length;
        int i = RandomUtil.randomInt(length);
        String s = WORD_LSIT[i];
        return s;

    }

    @Override
    public String help() {
        return null;
    }
}
