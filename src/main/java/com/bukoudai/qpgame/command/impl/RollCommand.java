package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.util.RandomUtil;
import com.bukoudai.qpgame.command.Command;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RollCommand implements Command {


    @Override
    public String execute(GroupMessageEvent event, long botId) {

        return String.valueOf(RandomUtil.randomInt(1010) + 97);

    }

    @Override
    public String help() {
        return "/roll ";
    }
}
