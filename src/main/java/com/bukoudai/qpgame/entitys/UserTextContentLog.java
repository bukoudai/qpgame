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
@TableName("user_text_content_log")
public class UserTextContentLog {

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 账号
   */
  private String user;

  private String nick;

  private String textContent;
  private String creatTime;
  private String creatDay;

}
