package com.bukoudai.qpgame.task;

import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.service.JokesService;
import com.bukoudai.qpgame.utlis.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
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
    private final JokesService jokesService;

    /**
     * 疫情定时发送
     */
    @Scheduled(cron = "${schedule.my_bot_task.test}")
    public void test() {

        ContactList<Group> groups = myBot.getGroups();
        for (Group group : groups) {
            Jokes jokes = jokesService.randomOneByType(JokesTypeEnum.DAILY_PROVERB);

            StringBuilder msg = new StringBuilder();
            msg.append(JokesTypeEnum.DAILY_PROVERB.getLabel()).append(":\r\n").append(jokes.getText());
            BotUtils.sendMsg(group, null, msg.toString());
        }

    }

}
