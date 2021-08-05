package com.bukoudai.qpgame.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import com.bukoudai.qpgame.mapper.UserPetsMapper;
import com.bukoudai.qpgame.service.UserPetsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserPetsServiceImpl extends ServiceImpl<UserPetsMapper, UserPets> implements UserPetsService, IService<UserPets> {


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
        return userPets;
    }

    @Override
    public List<String> pk(UserPets one, UserPets other) {


        return null;
    }
}
