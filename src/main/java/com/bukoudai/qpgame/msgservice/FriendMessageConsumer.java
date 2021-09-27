package com.bukoudai.qpgame.msgservice;

import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.command.CommandBuild;
import com.bukoudai.qpgame.mapper.JokesMapper;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service

public class FriendMessageConsumer implements Consumer<FriendMessageEvent> {

    @Value("${bot.your_qq_number}")
    private String yourQQNumber;
    @Autowired
    private CommandBuild commandBulid;
    @Autowired
    private JokesMapper jokesMapper;

    @Override
    public void accept(FriendMessageEvent event) {

        if (Long.parseLong(yourQQNumber) == (event.getSender().getId())) {

//            QueryWrapper<Jokes> wrapper = new QueryWrapper<Jokes>().eq("type", JokesTypeEnum.DAILY_PROVERB.getCode()) ;
//            Integer integer = jokesMapper.selectCount(wrapper);
//
//            int i = RandomUtil.randomInt(integer);
//            Page<Jokes> a =new Page<>(i,1);
//            IPage<Jokes> jokesIPage = jokesMapper.selectPage(a, wrapper);
//            Jokes jokes = jokesIPage.getRecords().get(0);
//
//
//            String msg = jokes.getText();
            String msg = "";
            Command bulid = commandBulid.build(event, 1);
            if (bulid != null) {
                msg = bulid.execute(event, 1);
            }

            event.getSubject().sendMessage(new MessageChainBuilder()
                    .append(new QuoteReply(event.getMessage()))
                    .append(msg)
                    .build()
            );
        }
    }
}
