package com.bukoudai.qpgame.thirdapi.translateapi;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Slf4j
public class TranslateApiProcess {


  public static Long appid;

  @Value("${tx.appid}")
  private void setAppid(String appId) {
    appid = Long.parseLong(appId);
  }

  private static String secret_id;

  @Value("${tx.secret_id}")
  private void setSecretId(String secretId) {
    secret_id = secretId;
  }

  private static String secret_key;

  @Value("${tx.secret_key}")
  private void setSecretKey(String secretKey) {
    secret_key = secretKey;
  }

  public static String doJob(Function<TmtClient, String> fun) {

    // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
    // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
    Credential cred = new Credential(secret_id, secret_key);
    // 实例化一个http选项，可选的，没有特殊需求可以跳过
    HttpProfile httpProfile = new HttpProfile();
    httpProfile.setEndpoint("tmt.tencentcloudapi.com");
    // 实例化一个client选项，可选的，没有特殊需求可以跳过
    ClientProfile clientProfile = new ClientProfile();
    clientProfile.setHttpProfile(httpProfile);
    // 实例化要请求产品的client对象,clientProfile是可选的
    TmtClient client = new TmtClient(cred, "ap-chengdu", clientProfile);
    // 实例化一个请求对象,每个接口都会对应一个request对象


    return fun.apply(client);

  }

}
