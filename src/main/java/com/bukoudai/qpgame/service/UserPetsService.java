package com.bukoudai.qpgame.service;

import com.bukoudai.qpgame.entitys.Bots;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import net.mamoe.mirai.Bot;

import java.util.List;

/**
 * 用户宠物
 */
public interface UserPetsService {



   /**
    * 查看宠物列表信息
    */
   void setMainPets(User user,UserPets userPets);

   /**
    * 查看宠物列表信息
    */
   List<UserPets> queryUserPets(User user);

   /**
    * 宠物升级
    */
   void upUserPets(User user,UserPets userPets);

   /**
    * 销毁宠物返回积分
    */
   void destroyUserPets(User user,UserPets userPets);



}