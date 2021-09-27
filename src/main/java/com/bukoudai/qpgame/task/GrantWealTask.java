package com.bukoudai.qpgame.task;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.mapper.JokesMapper;
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
    private final JokesMapper jokesMapper;

    /**
     * 疫情定时发送
     */
    @Scheduled(cron = "${schedule.my_bot_task.test}")
    public void test() {

        ContactList<Group> groups = myBot.getGroups();
        for (Group group : groups) {
            QueryWrapper<Jokes> wrapper = new QueryWrapper<Jokes>().eq("type", JokesTypeEnum.DAILY_PROVERB.getCode());
            Integer integer = jokesMapper.selectCount(wrapper);

            int i = RandomUtil.randomInt(integer);
            Page<Jokes> a = new Page<>(i, 1);
            IPage<Jokes> jokesIPage = jokesMapper.selectPage(a, wrapper);
            Jokes jokes = jokesIPage.getRecords().get(0);
            StringBuilder msg = new StringBuilder();
            msg.append(JokesTypeEnum.DAILY_PROVERB.getLabel()).append(":\r\n").append(jokes.getText());
            BotUtils.sendMsg(group, null, msg.toString());
        }

    }

}
