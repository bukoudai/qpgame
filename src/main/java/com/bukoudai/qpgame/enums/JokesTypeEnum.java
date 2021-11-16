package com.bukoudai.qpgame.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public enum JokesTypeEnum {


  DAILY_PROVERB(0, "每日小贴士", null),
  STORY(1, "故事", null),
  DIALOGUE(2, "对话", null),
  LQSAY(3, "刘强语录", null),

  CLASSIC_DIALOGUE(4, "经典台词", 7),
  PROVERB(5, "文化谚语", 6),
  NAOWAN(6, "脑筋急转弯", 5),
  DUJITANG(7, "毒鸡汤", 4),
  TIANGOU(8, "舔狗日记", 3),
  DICTUM(9, "名言警句", 2),
  MINGYAN(10, "名人名言", 1),
  ZMSC(11, "最美宋词", 0),
  ;

  private Integer code;
  private String label;
  private Integer week;


  public static JokesTypeEnum parse(Integer type) {
    if (type == null) {
      return null;
    }
    for (JokesTypeEnum value : JokesTypeEnum.values()) {
      if (value.getCode().equals(type)) {
        return value;
      }
    }
    return null;

  }

  public static JokesTypeEnum getByWeek() {

    for (JokesTypeEnum value : JokesTypeEnum.values()) {
      Integer week = value.getWeek();
      if (week != null) {
        if (week.equals(new Date().getDate() % 5)) {
          return value;
        }

      }
    }
    return null;

  }
}
