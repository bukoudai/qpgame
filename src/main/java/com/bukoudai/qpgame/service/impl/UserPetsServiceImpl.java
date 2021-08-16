package com.bukoudai.qpgame.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.Pets;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.mapper.PetsMapper;
import com.bukoudai.qpgame.mapper.UserPetsMapper;
import com.bukoudai.qpgame.service.UserPetsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserPetsServiceImpl extends ServiceImpl<UserPetsMapper, UserPets> implements UserPetsService, IService<UserPets> {

    private final PetsMapper petsMapper;

    @Override
    public void setMainPets(String loginNo, String userPetId) {

        baseMapper.clearMainPets(loginNo);
        baseMapper.setMainPets(loginNo, userPetId);

    }

    @Override
    public List<UserPets> queryUserPets(User user) {
        List<UserPets> userPets = baseMapper.queryUserPets(user);

        return userPets;
    }

    @Override
    public void upUserPets(User user, UserPets userPets) {

    }

    @Override
    public void destroyUserPets(User user, UserPets userPets) {

    }

    @Override
    public UserPets getMainPet(String loginNo) {
        QueryWrapper<UserPets> wrapper = new QueryWrapper<UserPets>().eq("login_no", loginNo).eq("is_main", "1");
        UserPets userPets = baseMapper.selectOne(wrapper);
        if (userPets == null) {
            //没有就选一个
            wrapper = new QueryWrapper<UserPets>().eq("login_no", loginNo);
            List<UserPets> userPets1 = baseMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(userPets1)) {
                userPets=userPets1.get(0);
            }


        }
        return userPets;
    }

    @Override
    public List<String> pk(UserPets one, String nick, UserPets other) {
        QueryWrapper<Pets> onewrapper = new QueryWrapper<>(Pets.builder().petId(one.getPetId()).build());
        QueryWrapper<Pets> otherwrapper = new QueryWrapper<>(Pets.builder().petId(other.getPetId()).build());
        Pets one1 = petsMapper.selectOne(onewrapper);
        Pets other1 = petsMapper.selectOne(otherwrapper);
        //达到攻击时需要的速度
        Integer ackBaseSeep = 1000;
        //积累速度
        Integer ackoneSeep = 0;
        Integer ackothSeep = 0;
        //各自速度
        Integer speed1 = one1.getSpeed();
        Integer speed2 = other1.getSpeed();


        List<String> re = new LinkedList<>();
        List<String> re2 = new LinkedList<>();
        String info = StrUtil.format("{}的宠物{} hp:{} 攻击力:{} 防御:{} 速度:{}  \r\n{}的宠物{} hp:{} 攻击力:{} 防御:{} 速度:{} ", nick, one1.getPetName(), one1.getHp(),one1.getPAtk(),one1.getPDef(),
                one1.getSpeed(),"对方", other1.getPetName(), other1.getHp(),other1.getPAtk(),other1.getPDef(),
                other1.getSpeed());

        re.add(info);
        re2.add(info);
        while (one1.getHp() > 0 && other1.getHp() > 0) {

            //1到达攻击时机
            ackoneSeep=ackoneSeep+speed1;
            if ((ackoneSeep ) - ackBaseSeep >= 0) {
                if (ack(nick, "对方", one1, other1, re)) {
                    break;
                }
            }
            //2到达攻击时机
            ackothSeep=ackothSeep+speed2;
            if ((ackothSeep ) - ackBaseSeep >= 0) {
                if (ack("对方", nick, other1, one1, re)) {
                    break;
                }
            }
        }

        re2.add(re.get(re.size()-1));
        return re2;
    }

    private boolean ack(String nick, String nick2, Pets one1, Pets other1, List<String> re) {
        //1攻击力
        Integer PAtk1 = one1.getPAtk();
        //2防御力
        Integer PDef2 = other1.getPDef();

        int hpd = PAtk1 - PDef2;
        if (hpd > 0) {
            int i = other1.getHp() - hpd;
            other1.setHp(i);
            String s = StrUtil.format("{}的宠物{} 进行攻击,{}的宠物{}受到{}点伤害 现有hp:{}", nick, one1.getPetName(), nick2, other1.getPetName(), hpd, i);

            re.add(s);
            if (i <= 0) {
                re.add(nick2 + "的宠物已战败");
                return true;
            }

        } else {
            int i = other1.getHp() - 1;
            other1.setHp(i);
            String s = StrUtil.format("{}的宠物{} 进行攻击,未能击破护甲,{}的宠物{}受到1点伤害 现有hp:{}", nick, one1.getPetName(), nick2, other1.getPetName(), i);
            re.add(s);
            if (i <= 0) {
                re.add(nick2 + "的宠物已战败");
                return true;
            }
        }
        return false;
    }
}
