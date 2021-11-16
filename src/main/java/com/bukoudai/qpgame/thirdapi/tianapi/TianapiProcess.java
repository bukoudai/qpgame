package com.bukoudai.qpgame.thirdapi.tianapi;

import cn.hutool.http.HttpUtil;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.enums.TianApiEnum;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;

public class TianapiProcess {
  public static Long appid;

  @Value("${txapi.key}")
  private void setAppid(String appId) {
    appid = Long.parseLong(appId);
  }


  public static String execute(TianApiEnum tianApiEnum) {

    HashMap<String, Object> map = new HashMap<>();
    map.put("key", appid);


    return HttpUtil.post(tianApiEnum.getUrl(), map);
  }

  public static void main(String[] args) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("key", appid);

    JokesTypeEnum byWeek = JokesTypeEnum.getByWeek();
    TianApiEnum tianApiEnum = TianApiEnum.parse(byWeek.getCode());
    System.out.println(tianApiEnum.getLabel());
    String post = HttpUtil.post(tianApiEnum.getUrl(), map);
    System.out.println(tianApiEnum.getLabel());
    System.out.println(tianApiEnum.getUrl());
    System.out.println(post);
  }

}
