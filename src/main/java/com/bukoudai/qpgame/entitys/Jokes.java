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
@TableName("jokes")
public class Jokes {

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   * 文本
   */
  private String text;
  /**
   * 类型
   */
  private int type;
  /**
   * 状态 0 未使用 1 已使用
   */
  private int status;


}
