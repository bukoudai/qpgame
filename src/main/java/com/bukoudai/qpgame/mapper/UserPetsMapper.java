package com.bukoudai.qpgame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bukoudai.qpgame.entitys.User;
import com.bukoudai.qpgame.entitys.UserPets;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserPetsMapper extends BaseMapper<UserPets> {

  /**
   * 查看宠物列表信息
   */
  @Select("SELECT user_pet_id,pet_name,pet_id,pet_nike,is_main FROM user_pets WHERE login_no =#{loginNo} ")
  List<UserPets> queryUserPets(User user);

  /**
   * 查询宠物数量
   */
  Integer queryUserPetsCount(User user);

  /**
   * 清除主宠
   *
   * @param loginNo
   */
  @Update("update user_pets set is_main = '0' WHERE login_no =#{loginNo} ")
  void clearMainPets(String loginNo);

  /**
   * 设置主宠
   *
   * @param loginNo
   * @param userPetId
   */
  @Update("update user_pets set is_main = '1' WHERE login_no =#{loginNo} and user_pet_id=#{userPetId}")
  void setMainPets(String loginNo, String userPetId);

  @Update("<script> update user_pets set is_delete = '1' WHERE login_no =#{loginNo}  " +
          "<if test=\"user_pet_id != null\">\n" +
          "and user_pet_id=#{userPetId} " +
          "</if> </script>")
  void destroyUserPets(String loginNo, String[] userPetIds);
}
