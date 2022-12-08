package com.bukoudai.qpgame.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * openai响应
 *
 {
 "id": "cmpl-6L8tco7dIqH41HEUPhG5drTL7igy9",
 "object": "text_completion",
 "created": 1670497104,
 "model": "text-davinci-003",
 "choices": [
 {
 "text": "\n\nYou're welcome.",
 "index": 0,
 "logprobs": null,
 "finish_reason": "stop"
 }
 ],
 "usage": {
 "prompt_tokens": 8,
 "completion_tokens": 6,
 "total_tokens": 14
 }
 }
 */
@Data
public class OpenAiResVo implements Serializable {
  /**
   * 序列化ID
   */
  private static final long serialVersionUID = -5809782578272943239L;


    /**
     * id
     */
    private String id;
    /**
     * object
     */
    private String object;
    /**
     * 创建时间
     */
    private Integer created;
    /**
     * 模型
     */
    private String model;
    /**
     * 选择
     */
    private List<OpenAiResChoicesVo> choices;
    /**
     * 使用
     */
    private OpenAiResUsageVo usage;
}
