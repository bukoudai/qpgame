package com.bukoudai.qpgame.command.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.UserPoints;
import com.bukoudai.qpgame.enums.CommandEnum;
import com.bukoudai.qpgame.enums.TransLateLangEnum;
import com.bukoudai.qpgame.mapper.UserPointsMapper;
import com.bukoudai.qpgame.thirdapi.translateapi.TranslateApiService;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslateApiCommand implements Command {

  private final UserPointsMapper userPointsMapper;
  public static final int ONE_CONSUME_POINTS = 10;

  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {
    String s = event.getMessage().contentToString();
    long senderId = event.getSender().getId();

    UserPoints userPoints = userPointsMapper.selectOne(new QueryWrapper<>(UserPoints.builder().loginNo(senderId).build()));
    if (userPoints == null) {
      return SendMsgVo.msg("积分不足");
    }


    if ((userPoints.getPoints() - ONE_CONSUME_POINTS < 0)) {
      return SendMsgVo.msg("积分不足");
    }
    userPoints.setPoints(userPoints.getPoints() - ONE_CONSUME_POINTS);
    userPointsMapper.updateById(userPoints);
    String s2 = s.replaceFirst(" ", SPLIS_WORDS);

    String[] s1 = s2.split(SPLIS_WORDS);
    TransLateLangEnum defaultTarget;
    String text = null;

    if (s1.length > 1) {
      //第一个参数 待翻译数据
      text = s1[1];
      if ("?".equals(text)) {

        return SendMsgVo.msg(help());
      }
    }
    // 翻译目标语言
    String cmd = s1[0];
    String target = cmd.replaceAll(CommandEnum.TRANSLATE_COMMAND.getKey(), "");
    TransLateLangEnum parse = TransLateLangEnum.parse(target);
    String languageDetect = TranslateApiService.languageDetect(text);
    if (parse != null) {
      defaultTarget = parse;
    } else {
      //如果未指定语种 则中文自动翻译为英文 非中文翻译为中文
      if (TransLateLangEnum.ZH.getCode().equals(languageDetect)) {
        defaultTarget = TransLateLangEnum.EN;
      } else {
        defaultTarget = TransLateLangEnum.ZH;
      }
    }

    return SendMsgVo.msg(TranslateApiService.textTranslate(text, languageDetect, defaultTarget));


  }

  @Override
  public String help() {
    StringBuilder help = new StringBuilder();
    for (TransLateLangEnum value : TransLateLangEnum.values()) {
      help.append(value.getLabel()).append("  ").append(value.getCode()).append("\n");
    }


    return "/翻译[lang] 带翻译文本 \n" +
            "lang:\n" + help;
  }
}
