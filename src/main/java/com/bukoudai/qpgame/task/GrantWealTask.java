package com.bukoudai.qpgame.task;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.bukoudai.qpgame.thirdapi.FyInfo;
import com.bukoudai.qpgame.utlis.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Slf4j
@Component
@AllArgsConstructor
public class GrantWealTask {


    private final Bot myBot;

    /**
     * 疫情定时发送
     */
    @Scheduled(cron = "${schedule.my_bot_task.test}")
    public void test() {

        ContactList<Group> groups = myBot.getGroups();



            for (Group group : groups) {
                BotUtils.sendMsg(group, null, "早上好");
            }

    }

}
