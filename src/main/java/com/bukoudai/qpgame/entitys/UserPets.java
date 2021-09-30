package com.bukoudai.qpgame.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.hutool.core.text.CharSequenceUtil.format;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_pets")
public class UserPets {

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Integer userPetId;

  /**
   * 账号
   */
  private Long loginNo;

  /**
   * 积分
   */
  private String petName;

  private Integer petId;
  private String petNike;
  private String isMain;
  private Integer isDelete;

  @Override
  public String toString() {

    return format("id:{},昵称:{},名称:{}", userPetId, petNike, petName);


  }
}
