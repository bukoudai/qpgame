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
@TableName("user_text_content_pic")
public class UserTextContentPic {

  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Long id;
  private Long userTextContentLogId;


  private String url;
  private String bucketName;
  private String picName;
  private String bucketKey;


}
