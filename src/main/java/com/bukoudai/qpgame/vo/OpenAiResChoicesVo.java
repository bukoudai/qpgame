package com.bukoudai.qpgame.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *{
 *  "text": "\n\nYou're welcome.",
 *  "index": 0,
 *  "logprobs": null,
 *  "finish_reason": "stop"
 *  }
 */
@Data
public class OpenAiResChoicesVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文本
     */
    private String text;
    /**
     * 索引
     */
    private Integer index;
    /**
     * 日志概率
     */
    private String logprobs;
    /**
     * 完成原因
     */
    private String finish_reason;


}
