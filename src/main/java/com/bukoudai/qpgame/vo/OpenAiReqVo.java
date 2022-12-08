package com.bukoudai.qpgame.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * openai请求
 *
 * {
 *   "model":"text-davinci-003",
 *   "prompt": "谢谢你",
 *   "max_tokens": 4000,
 *   "temperature": 0.5
 * }
 */
@Data
public class OpenAiReqVo implements Serializable {
  /**
   * 序列化ID
   */
  private static final long serialVersionUID = -5809782578272943239L;


    /**
     * 模型
     */
    private String model;
    /**
     * 提示
     */
    private String prompt;
    /**
     * 最大token
     */
    private Integer max_tokens;
    /**
     * 温度
     */
    private Double temperature;
}
