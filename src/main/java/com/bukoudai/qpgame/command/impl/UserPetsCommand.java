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
        long senderId = event.getSender().getId();
        String key1 = "";
        if (s1.length > 1) {
            key1 = s1[1];

        }
        if ("-l".equals(key1)) {
            List<UserPets> userPets = userPetsService.queryUserPets(User.builder().loginNo(senderId).build());
            return userPets.toString();
        } else if ("-m".equals(key1)) {
            String key2;
            if (s1.length > 2) {
                key2 = s1[2];
                userPetsService.setMainPets(String.valueOf(senderId), key2);
                return "可能成功了 ";

            } else {

                UserPets re = userPetsService.getMainPet(String.valueOf(senderId));

                return re == null ? "未设置主要宠物" : re.toString();
            }


        } else {
            return "指令错误 现有指令 -l ";
        }


    }
}
