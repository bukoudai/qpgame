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
    private final static String LIST_KEY="-l";
    private final static String MAIN_KEY="-m";
    private final static String DESTROY_KEY="-d";


    @Override
    public String execute(GroupMessageEvent event, long botId) {

        String s = event.getMessage().contentToString();
        String[] s1 = s.split(" ");
        long senderId = event.getSender().getId();
        String key1 = "";
        if (s1.length > 1) {
            key1 = s1[1];

        }
        if (LIST_KEY.equals(key1)) {
            List<UserPets> userPets = userPetsService.queryUserPets(User.builder().loginNo(senderId).build());
            return userPets.toString();
        } else if (MAIN_KEY.equals(key1)) {
            String key2;
            if (s1.length > 2) {
                key2 = s1[2];
                userPetsService.setMainPets(String.valueOf(senderId), key2);
                return "设置出战宠物可能成功了 请使用 /pet -m 自己查询结果 ";
            } else {
                UserPets re = userPetsService.getMainPet(String.valueOf(senderId));
                return re == null ? "未设置主要宠物" : re.toString();
            }
        }else if (DESTROY_KEY.equals(key1)) {
            String key2;
            if (s1.length > 2) {
                key2 = s1[2];
                userPetsService.destroyUserPets(String.valueOf(senderId), key2);
                return "成功";
            } else {
                userPetsService.destroyUserPets(String.valueOf(senderId), null);
                return  "成功";
            }
        }
        return "指令错误 现有指令"+help();
    }

    @Override
    public String help() {
        return "/pet -l 查看所有宠物 \n" +
                "/pet -m 查看出战宠物 \n" +
                "/pet -m [id] 设置出战宠物 id为宠物id   \n"+
                "/pet -d [id] 销毁指定宠物 id为宠物id   \n"+
                "/pet -d 销毁除出战宠物外其他宠物 \n";
    }
}
