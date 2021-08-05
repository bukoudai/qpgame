package com.bukoudai.qpgame.msgservice;

import cn.hutool.core.util.StrUtil;
import net.mamoe.mirai.event.events.GroupTalkativeChangeEvent;
import net.mamoe.mirai.event.events.MemberHonorChangeEvent;

import java.util.function.Consumer;

/**
 * 龙王事件通报
 */
public class GroupTalkativeChangeEventConsumer implements Consumer<GroupTalkativeChangeEvent> {


    @Override
    public void accept(GroupTalkativeChangeEvent event) {

        String nickPre = event.getPrevious().getNick();
        String nickNow = event.getNow().getNick();

        event.getGroup().sendMessage(StrUtil.format("龙王已更换 前龙王:{}  现龙王:{} ",nickPre ,nickNow));
    }
}
