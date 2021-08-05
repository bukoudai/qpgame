package com.bukoudai.qpgame.msgservice;

import net.mamoe.mirai.event.events.MemberCardChangeEvent;

import java.util.function.Consumer;

/**
 * 修改名片修复
 */
public class MemberCardChangeEventConsumer implements Consumer<MemberCardChangeEvent> {

    private Long botId;

    public  MemberCardChangeEventConsumer (long botId){
        this.botId=botId;
    }
    @Override
    public void accept(MemberCardChangeEvent event) {
        String origin = event.getOrigin();
        event.getMember().setNameCard(origin);
    }
}
