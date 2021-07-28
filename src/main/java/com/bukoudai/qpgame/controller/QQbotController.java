package com.bukoudai.qpgame.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.entitys.Pets;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.entitys.UserPoints;
import com.bukoudai.qpgame.enums.PetRarityEnum;
import com.bukoudai.qpgame.mapper.PetsMapper;
import com.bukoudai.qpgame.mapper.UserPetsMapper;
import com.bukoudai.qpgame.mapper.UserPointsMapper;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Api(value = "qq机器人"
)
@RestController
@RequestMapping("/bot")
@AllArgsConstructor
public class QQbotController {


    private final JdbcTemplate jdbcTemplate;

    private final UserPointsMapper userPointsMapper;

    private final PetsMapper petsMapper;
    private final UserPetsMapper userPetsMapper;

    @PostMapping("/test")
    public void test() {

        //登录用户
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM bots LIMIT 1 ");
        maps.forEach(System.out::print);
        Map<String, Object> map = maps.get(0);
        String deviceJson = (String) map.get("device_json");
        String loginPassword = (String) map.get("login_password");
        Integer loginNo = (Integer) map.get("login_no");
        BotConfiguration botConfiguration = new BotConfiguration();
        botConfiguration.loadDeviceInfoJson(deviceJson);
        Bot bot = BotFactory.INSTANCE.newBot(loginNo, loginPassword, botConfiguration);
        bot.login();
        long botId = bot.getId();

        long yourQQNumber = 1111111;
        bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, (event) -> {
            if (event.getSender().getId() == yourQQNumber) {
                event.getSubject().sendMessage(new MessageChainBuilder()
                        .append(new QuoteReply(event.getMessage()))
                        .append("Hi, you just said: '")
                        .append(event.getMessage())
                        .append("'")
                        .build()
                );
            }
        });

        bot.getEventChannel().subscribeAlways(GroupMessageEvent.class, (event) -> {
            System.out.println(event);
            long senderId = event.getSender().getId();
            MessageChain message = event.getMessage();
            UserPoints userPoints = userPointsMapper.selectOne(new QueryWrapper<>(UserPoints.builder().loginNo(senderId).build()));
            AtomicBoolean doFlag = new AtomicBoolean(false);
            message.forEach(singleMessage -> {
                if (singleMessage instanceof At) {
                    At at = (At) singleMessage;
                    if (at.getTarget() == botId) {
                        String content = message.contentToString();
                        if (content.contains("抽卡")) {
                            if (userPoints == null) {
                                event.getSubject().sendMessage(new MessageChainBuilder()
                                        .append(new QuoteReply(event.getMessage()))
                                        .append("积分不足")
                                        .build()
                                );
                                doFlag.set(true);
                                return;
                            }
                            int count = 1;
                            if ((userPoints.getPoints() - count >= 0)) {
                                LinkedList<Pets> pets = new LinkedList<>();
                                for (int i = 0; i < count; i++) {
                                    pets.add(petsMapper.roundGetOne());
                                }
                                StringBuilder re = new StringBuilder();
                                pets.forEach(pets1 ->
                                        {
                                            String petRarity = Objects.requireNonNull(PetRarityEnum.parse(pets1.getPetRarity())).getLabel();
                                            re.append(pets1.getPetName()).append("|").append(petRarity).append("|").append(pets1.getPetType());
                                            UserPets build = UserPets.builder().loginNo(senderId).petName(pets1.getPetName()).petNike(pets1.getPetName()).petId(pets1.getPetId()).build();
                                            userPetsMapper.insert(build);
                                        }
                                );

                                userPoints.setPoints(userPoints.getPoints() - 1);
                                userPointsMapper.updateById(userPoints);
                                event.getSubject().sendMessage(new MessageChainBuilder()
                                        .append(new QuoteReply(event.getMessage()))
                                        .append("结果:")
                                        .append(re)
                                        .append("|")
                                        .append("剩余积分:")
                                        .append(String.valueOf(userPoints.getPoints()))
                                        .build()
                                );

                            } else {
                                event.getSubject().sendMessage(new MessageChainBuilder()
                                        .append(new QuoteReply(event.getMessage()))
                                        .append("积分不足,当前积分:")
                                        .append(String.valueOf(userPoints.getPoints()))
                                        .build()
                                );
                            }

                            doFlag.set(true);
                        }


                    }
                }
            });
            if (doFlag.get()) {
                return;
            }
            //加积分

            if (userPoints == null) {
                userPointsMapper.insert(UserPoints.builder().loginNo(senderId).points(1).build());
            } else {

                userPoints.setPoints(userPoints.getPoints() + 1);
                userPointsMapper.updateById(userPoints);
            }

            if (doFlag.get()) {
                return;
            }


            if ("投骰子".equals(event.getMessage().contentToString())) {

                event.getSubject().sendMessage(new MessageChainBuilder()
                        .append(new QuoteReply(event.getMessage()))

                        .append(String.valueOf(RandomUtil.randomInt(1010) + 97))

                        .build()
                );
                doFlag.set(true);
            }


        });


    }


}

