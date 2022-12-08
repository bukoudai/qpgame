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
@TableName("sys_config")
public class SysConfig {

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Long id;



  private String configKey;

  private String configValue;

}
