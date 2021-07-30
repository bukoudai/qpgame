package com.bukoudai.qpgame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserPetsMapper extends BaseMapper<UserPets> {

    /**
     * 查看宠物列表信息
     */
    @Select("SELECT user_pet_id,pet_name,pet_id,pet_nike FROM user_pets WHERE login_no =#{loginNo} ")
    List<UserPets> queryUserPets(User user);

    /**
     * 查询宠物数量
     */
   Integer queryUserPetsCount(User user);






}
