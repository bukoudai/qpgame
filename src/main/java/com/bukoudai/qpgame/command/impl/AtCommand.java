package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.configuration.CommandConfig;
import com.bukoudai.qpgame.entitys.SysConfig;
import com.bukoudai.qpgame.service.SysConfigService;
import com.bukoudai.qpgame.vo.OpenAiReqVo;
import com.bukoudai.qpgame.vo.OpenAiResVo;
import com.bukoudai.qpgame.vo.SendMsgVo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtCommand implements Command {
  private static final String[] WORD_LSIT = new String[]{"翻滚吧牛宝宝", "呵呵", "略略略", "凸 -.- 凸", "我是你爸爸"};

  private final SysConfigService sysConfigService;

  @Override
  public SendMsgVo execute(MessageEvent event, long botId) {
    int length = WORD_LSIT.length;
    int i = RandomUtil.randomInt(length);
  Boolean useOpenAI =false;
    SysConfig useOpenAIV = sysConfigService.lambdaQuery().eq(SysConfig::getConfigKey, "useOpenAI").one();

    if (useOpenAIV != null&&useOpenAIV.getConfigValue().equals("1")) {
      useOpenAI=true;
    }

    SysConfig maxTokens = sysConfigService.lambdaQuery().eq(SysConfig::getConfigKey, "maxTokens").one();
    SysConfig temperature = sysConfigService.lambdaQuery().eq(SysConfig::getConfigKey, "temperature").one();
    SysConfig model = sysConfigService.lambdaQuery().eq(SysConfig::getConfigKey, "model").one();
    String prompt = event.getMessage().contentToString();
    String msg=WORD_LSIT[i];
    if (useOpenAI) {
      //使用Post请求
      String url = "https://api.openai.com/v1/completions";
      OpenAiReqVo openAiReqVo = new OpenAiReqVo();
      openAiReqVo.setModel(model.getConfigValue());
      openAiReqVo.setPrompt(prompt);
      openAiReqVo.setMax_tokens(Integer.valueOf(maxTokens.getConfigValue()));
      openAiReqVo.setTemperature(Double.valueOf(temperature.getConfigValue()));
      System.out.println(CommandConfig.OPENAI_API_KEY);
      String body = HttpRequest.post(url).header("Authorization", CommandConfig.OPENAI_API_KEY).timeout(50000).body(JSONUtil.toJsonStr(openAiReqVo)).execute().body();
      OpenAiResVo openAiResVo = JSONUtil.toBean(body, OpenAiResVo.class);
      msg=  openAiResVo.getChoices().get(0).getText();

    }



    return SendMsgVo.msg(msg);

  }

  @Override
  public String help() {
    return null;
  }
}
