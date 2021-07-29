package com.bukoudai.qpgame.task;

import cn.hutool.json.JSONObject;
import com.bukoudai.qpgame.thirdapi.FyInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
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
        StringBuilder msgB = new StringBuilder("今日疫情信息:");

        JSONObject fyCityDtail = FyInfo.getFyCityDtail("四川省", "成都");
        msgB.append(fyCityDtail == null ? "" : fyCityDtail.toStringPretty());

        String msg = msgB.toString();
        if (StringUtils.isNotBlank(msg)) {

//            myBot.getFriends().forEach(friend -> friend.sendMessage(msg));
            myBot.getGroups().forEach(group -> group.sendMessage(msg));

//            myBot.getGroups().forEach(group -> group.sendMessage(msg));


        }


    }

}
