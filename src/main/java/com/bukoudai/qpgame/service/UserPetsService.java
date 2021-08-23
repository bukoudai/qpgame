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
    * 设置主宠
    */
   void setMainPets(String loginNo,String userPetId);

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
   void destroyUserPets(String loginNo,String... userPetIds);

   /**
    * 查询主要宠物
    * @param loginNo
    * @return
    */
   UserPets getMainPet(String loginNo);

   /**
    * pk方法 返回对战描述列表
    * @param one
    * @param other
    * @return
    */
   List<String> pk(UserPets one,String nick,UserPets other);
}
