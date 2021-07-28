package com.bukoudai.qpgame.command;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.entitys.Pets;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.entitys.UserPoints;
import com.bukoudai.qpgame.enums.PetRarityEnum;
import com.bukoudai.qpgame.mapper.PetsMapper;
import com.bukoudai.qpgame.mapper.UserPetsMapper;
import com.bukoudai.qpgame.mapper.UserPointsMapper;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Objects;
@Service
@AllArgsConstructor
public class DrawCardCommand implements Command {


    private final UserPointsMapper userPointsMapper;

    private final PetsMapper petsMapper;
    private final UserPetsMapper userPetsMapper;

    @Override
    public String execute(GroupMessageEvent event, long botId) {
        String reMsg;
        System.out.println(event);
        long senderId = event.getSender().getId();

        UserPoints userPoints = userPointsMapper.selectOne(new QueryWrapper<>(UserPoints.builder().loginNo(senderId).build()));
        String content = event.getMessage().contentToString();
        if (userPoints == null) {
            return "积分不足";
        }
        String[] split = content.split(" ");
        int count = 1;
        if (split.length > 1) {
            String s = split[1];

            if (NumberUtil.isInteger(s)) {
                count = Integer.parseInt(s);
            }

        }


        if ((userPoints.getPoints() - count >= 0)) {
            LinkedList<Pets> pets = new LinkedList<>();
            for (int i = 0; i < count; i++) {
                pets.add(petsMapper.roundGetOne());
            }
            StringBuilder re = new StringBuilder();
            pets.forEach(pets1 ->
                    {
                        String petRarity = Objects.requireNonNull(PetRarityEnum.parse(pets1.getPetRarity())).getLabel();
                        re.append(pets1.getPetName()).append("|").append(petRarity).append("|").append(pets1.getPetType()).append("\r\n");
                        UserPets build = UserPets.builder().loginNo(senderId).petName(pets1.getPetName()).petNike(pets1.getPetName()).petId(pets1.getPetId()).build();
                        userPetsMapper.insert(build);
                    }
            );

            userPoints.setPoints(userPoints.getPoints() - count);
            userPointsMapper.updateById(userPoints);

            reMsg = StrUtil.format("结果:\r\n{}剩余积分:{} ", re, userPoints.getPoints());

        } else {

            reMsg = StrUtil.format("积分不足,当前积分:{}", userPoints.getPoints());

        }

        return reMsg;
    }
}