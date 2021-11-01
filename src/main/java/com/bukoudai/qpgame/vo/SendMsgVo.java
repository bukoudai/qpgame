package com.bukoudai.qpgame.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息对象
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMsgVo {
  /**
   * 消息
   */
  private String message;
  /**
   * 是否需要回复
   */
  private Boolean needReply;

  public static SendMsgVo msg(String msg) {
    return new SendMsgVo(msg, true);
  }
}
