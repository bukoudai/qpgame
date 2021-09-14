package com.bukoudai.qpgame.thirdapi.translateapi;

import com.bukoudai.qpgame.enums.TransLateLangEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 翻译请求包装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransLateReq {


    String text;
    String source;
    TransLateLangEnum target;
}
