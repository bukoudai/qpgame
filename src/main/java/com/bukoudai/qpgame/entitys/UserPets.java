package com.bukoudai.qpgame.entitys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Override
    public String toString() {

        return StrUtil.format("id:{},昵称:{},名称:{}", userPetId, petNike, petName);


    }
}
