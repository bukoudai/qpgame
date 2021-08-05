package com.bukoudai.qpgame.msgservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.MemberCardChangeEvent;

import java.util.function.Consumer;

/**
 * 修改名片修复
 */
@Slf4j
public class MemberCardChangeEventConsumer implements Consumer<MemberCardChangeEvent> {

    private final Long botId;
    private final UserService userService;

    public MemberCardChangeEventConsumer(long botId, UserService userService) {
        this.botId = botId;
        this.userService = userService;
    }

    @Override
    public void accept(MemberCardChangeEvent event) {

        NormalMember member = event.getMember();
        long memberId = member.getId();
        String nick = member.getNick();

        if (botId != memberId) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("login_no", memberId);
            User one = userService.getOne(userQueryWrapper);
            if (one == null) {
                one = User.builder().loginNo(memberId).role(2).nick(nick).build();
                userService.save(one);
            } else {
                if (!nick.equals(one.getNick())) {
                    log.info("{}设置昵称{}",memberId,nick);
                    member.setNameCard(nick);
                }
            }
        }
    }
}
