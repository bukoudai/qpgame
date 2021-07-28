package com.bukoudai.qpgame.command;

import cn.hutool.core.util.RandomUtil;
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
}
