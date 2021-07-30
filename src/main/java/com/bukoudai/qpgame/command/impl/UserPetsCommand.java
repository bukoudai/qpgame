package com.bukoudai.qpgame.command.impl;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.service.UserPetsService;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserPetsCommand implements Command {


    private final UserPetsService userPetsService;


    @Override
    public String execute(GroupMessageEvent event, long botId) {

        String s = event.getMessage().contentToString();
        String[] s1 = s.split(" ");

        String key = "";
        if (s1.length > 1) {
            key = s1[1];

        }
        if ("-l".equals(key)) {

            long senderId = event.getSender().getId();
            List<UserPets> userPets = userPetsService.queryUserPets(User.builder().loginNo(senderId).build());
            return userPets.toString();
        } else {
            return "指令错误 现有指令 -l ";
        }


    }
}
