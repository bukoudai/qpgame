package com.bukoudai.qpgame.task;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bukoudai.qpgame.thirdapi.FyInfo;
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
        StringBuilder msgB = new StringBuilder("今日疫情信息:");

        JSONObject fyCityDtail = FyInfo.getFyCityDtail("四川省", "成都");
        msgB.append(fyCityDtail == null ? "" : fyCityDtail.toStringPretty());
        ContactList<Group> groups = myBot.getGroups();

        String msg = msgB.toString();
        if (StringUtils.isNotBlank(msg)) {
            for (Group group : groups) {
                group.sendMessage(msg);
            }
        }
        msgB = new StringBuilder();
        msgB.append("今日疫情新闻:\r\n");
        msgB.append(JSONUtil.parseObj(FyInfo.getFyNews()).toStringPretty());
        msg = msgB.toString();
        if (StringUtils.isNotBlank(msg)) {
            for (Group group : groups) {
                group.sendMessage(msg);
            }
        }
    }

}
