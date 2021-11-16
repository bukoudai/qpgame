package com.bukoudai.qpgame.enums;

import com.bukoudai.qpgame.vo.TianMINGYANVo;
import com.bukoudai.qpgame.vo.TianZMSCVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否开启自动翻译
 */
@AllArgsConstructor
@Getter
public enum TianApiEnum {

  /**
   * 经典台词
   */
  classic_dialogue(JokesTypeEnum.CLASSIC_DIALOGUE.getCode(), "http://api.tianapi.com/dialogue/index", "经典台词", null),
  /**
   * 文化谚语
   */
  proverb(JokesTypeEnum.PROVERB.getCode(), "http://api.tianapi.com/proverb/index", "文化谚语", null),
  /**
   * 脑筋急转弯
   */
  naowan(JokesTypeEnum.NAOWAN.getCode(), "http://api.tianapi.com/naowan/index", "脑筋急转弯", null),
  /**
   * 毒鸡汤
   */
  dujitang(JokesTypeEnum.DUJITANG.getCode(), "http://api.tianapi.com/dujitang/index", "毒鸡汤", null),
  /**
   * 舔狗日记
   */
  tiangou(JokesTypeEnum.TIANGOU.getCode(), "http://api.tianapi.com/tiangou/index", "舔狗日记", null),
  /**
   * 名言警句
   */
  dictum(JokesTypeEnum.DICTUM.getCode(), "http://api.tianapi.com/dictum/index", "名言警句", null),
  /**
   * 名人名言
   */
  mingyan(JokesTypeEnum.MINGYAN.getCode(), "http://api.tianapi.com/mingyan/index", "名人名言", TianMINGYANVo.class),

  /**
   * 最美宋词
   */
  zmsc(JokesTypeEnum.ZMSC.getCode(), "http://api.tianapi.com/zmsc/index", "最美宋词", TianZMSCVo.class),
  ;


  private Integer code;
  private String url;
  private String label;
  private Class entity;


  public static TianApiEnum parse(Integer type) {
    if (type == null) {
      return null;
    }
    for (TianApiEnum value : TianApiEnum.values()) {
      if (value.getCode().equals(type)) {
        return value;
      }
    }
    return null;

  }
}
