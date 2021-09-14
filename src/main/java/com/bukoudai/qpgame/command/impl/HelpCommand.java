package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.enums.CommandEnum;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HelpCommand implements Command {


    @Override
    public String execute(MessageEvent event, long botId) {

        StringBuilder help = new StringBuilder();

        for (CommandEnum value : CommandEnum.values()) {
            help.append(value.getKey()).append(" ").append(value.getLabel()).append("\r\n");

        }


        return help.toString();

    }

    @Override
    public String help() {
        return "/help 帮助";
    }
}
