package com.bukoudai.qpgame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;

import java.util.List;

public interface UserPetsMapper extends BaseMapper<UserPets> {

    /**
     * 查看宠物列表信息
     */
    List<UserPets> queryUserPets(User user);

    /**
     * 查询宠物数量
     */
   Integer queryUserPetsCount(User user);






}
