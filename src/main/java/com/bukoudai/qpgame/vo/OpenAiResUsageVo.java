package com.bukoudai.qpgame.vo;

import lombok.Data;

import java.io.Serializable;

/**
 *{
 *  "prompt_tokens": 8,
 *  "completion_tokens": 6,
 *  "total_tokens": 14
 *  }
 */
@Data
public class OpenAiResUsageVo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 提示token
     */
    private Integer prompt_tokens;
    /**
     * 完成token
     */
    private Integer completion_tokens;
    /**
     * 总token
     */
    private Integer total_tokens;

}
