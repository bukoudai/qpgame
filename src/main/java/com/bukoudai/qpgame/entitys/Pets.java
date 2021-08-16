package com.bukoudai.qpgame.entitys;

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
@TableName("pets")
public class Pets {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer petId;

    /**
     * 宠物名称
     */
    private String petName;
    /**
     * 种族
     */
    private String petType;

    /**
     * 稀有度  0 普通 1稀有 2 史诗 3传说
     */
    private Integer petRarity;
    /**
     * 血量
     */
    private Integer hp;
    /**
     * '物攻'
     */
    private Integer pAtk;
    /**
     * '法攻'
     */
    private Integer mAtk;
    /**
     * '物防'
     */
    private Integer pDef;
    /**
     * '魔防'
     */
    private Integer mDef;
    /**
     * '速度'
     */
    private Integer speed;
}
