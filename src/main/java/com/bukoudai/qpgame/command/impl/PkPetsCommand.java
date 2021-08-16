package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.util.StrUtil;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.service.UserPetsService;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.SingleMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PkPetsCommand implements Command {


    private final UserPetsService userPetsService;


    @Override
    public String execute(GroupMessageEvent event, long botId) {
        Long atId = null;
        Member member = event.getSender();
        long sengId = member.getId();
        for (SingleMessage singleMessage : event.getMessage()) {

            if (singleMessage instanceof At) {
                At a = (At) singleMessage;
                atId = a.getTarget();

            }

        }
        if (atId == null) {
            return "指令错误 现有指令 -l ";
        } else {

            UserPets mainPet = userPetsService.getMainPet(String.valueOf(sengId));
            if (mainPet == null) {
                return "你还没有宠物";
            }
            UserPets otherPet = userPetsService.getMainPet(String.valueOf(atId));
            if (otherPet == null) {
                return "对方没有宠物";
            }
            List<String> pk = userPetsService.pk(mainPet,member.getNick(), otherPet);

            String s = StrUtil.join("\r\n", pk);
            return s;
        }


    }
}