package com.bukoudai.qpgame.thirdapi;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FyInfo {
  //云市场分配的密钥Id
  private static String secretId;

  @Value("${yysj.secret_id}")
  private void setSecretId(String secret_id) {
    secretId = secret_id;
  }

  //云市场分配的密钥Key

  private static String secretKey;

  @Value("${yysj.secret_key}")
  private void setSecretKey(String secret_key) {
    secretKey = secret_key;
  }

  private final static String SOURCE = "market";


  private static String fy_news_url;

  @Value("${yysj.fy_news_url}")
  private void setFyNewsUrl(String fyNewsUrl) {
    fy_news_url = fyNewsUrl;
  }

  private static String fy_details;

  @Value("${yysj.fy_details}")
  private void setFyDetails(String fyDetails) {
    fy_details = fyDetails;
  }

  public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
          throws NoSuchAlgorithmException, InvalidKeyException {
    String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
    Mac mac = Mac.getInstance("HmacSHA1");
    Key sKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
    mac.init(sKey);
    byte[] hash = mac.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
    String sig = Base64.encode(hash);

    String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
    return auth;
  }

  public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<?, ?> entry : map.entrySet()) {
      if (sb.length() > 0) {
        sb.append("&");
      }
      sb.append(String.format("%s=%s",
              URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
              URLEncoder.encode(entry.getValue().toString(), "UTF-8")
      ));
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String fyNews = FyInfo.getFyNews();
    System.out.println(fyNews);
  }

  public static JSONObject getFyCityDtail(String provinceName, String city) {
    JSONObject provinceDtail = getFyProvinceDtail(provinceName);
    if (provinceDtail != null) {
      JSONArray cityList = provinceDtail.getJSONArray("cityList");
      JSONArray dangerAreas = provinceDtail.getJSONArray("dangerAreas");
      if (cityList != null) {
        for (JSONObject jsonObject : cityList.jsonIter()) {
          if (city.equals(jsonObject.getStr("cityName"))) {
            jsonObject.set("dangerAreas", dangerAreas);
            jsonObject.set("updateTime", provinceDtail.get("updateTime"));
            jsonObject.set("todayStatictic", provinceDtail.get("todayStatictic"));
            return jsonObject;
          }

        }

      }


    }
    return null;
  }

  public static JSONObject getFyProvinceDtail(String provinceName) {
    try {
      String s = FyInfo.getFyInfo(fy_details);
      JSONObject resBody = JSONUtil.parseObj(s).getJSONObject("showapi_res_body");
      JSONArray jsonArray = resBody.getJSONArray("todayDetailList");
      if (jsonArray != null) {
        for (JSONObject jsonObject : jsonArray.jsonIter()) {

          if (provinceName.equals(jsonObject.getStr("provinceName"))) {
            jsonObject.set("updateTime", resBody.get("updateTime"));
            jsonObject.set("todayStatictic", resBody.get("todayStatictic"));
            return jsonObject;
          }

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }


  public static String getAllFyDtail() {
    return FyInfo.getFyInfo(fy_details);
  }

  public static String getFyNews() {
    return FyInfo.getFyInfo(fy_news_url);
  }

  public static JSONArray getFyNewsJSONArray(Boolean detail) {
    JSONObject object = JSONUtil.parseObj(FyInfo.getFyNews());
    JSONArray reArray = new JSONArray();
    JSONArray fyNewsJSONArray = object.getJSONObject("showapi_res_body").getJSONArray("newsList");
    for (JSONObject o : fyNewsJSONArray.jsonIter()) {
      o.remove("summary");
      o.remove("provinceName");
      if (detail && "新浪".equals(o.getStr("infoSource"))) {
        String url = o.getStr("sourceUrl");
        String html = HttpUtil.get(url);
        List<String> titles = ReUtil.findAll("<div class=\"article\" id=\"article\">(.*?)<!-- 正文 end -->", html, 1);
        String join = "";
        for (String title : titles) {
          String context = HtmlUtil.cleanHtmlTag(HtmlUtil.filter(title));
          String[] contextList = context.replaceAll("[\r\n\t]", "").split("　");
          LinkedList<String> jionList = new LinkedList<>();
          for (String row : contextList) {
            if (StrUtil.isNotBlank(row)) {
              jionList.add(row);
            }
          }
          jionList.removeFirst();
          jionList.removeLast();
          join = StrUtil.join("", jionList);
        }
        o.set("context", join);
      }
      reArray.add(o);
    }
    return reArray;


  }

  public static JSONArray getFyNewsJSONArray() {
    return getFyNewsJSONArray(Boolean.FALSE);
  }

  private static String getFyInfo(String urlFyInfo) {


    StringBuilder result = new StringBuilder();
    BufferedReader in = null;
    try {
      Calendar cd = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
      sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
      String datetime = sdf.format(cd.getTime());
      // 签名
      String auth = calcAuthorization(SOURCE, secretId, secretKey, datetime);
      // 请求方法
      String method = "GET";
      // 请求头
      Map<String, String> headers = new HashMap<>();
      headers.put("X-Source", SOURCE);
      headers.put("X-Date", datetime);
      headers.put("Authorization", auth);


      // body参数
      Map<String, String> bodyParams = new HashMap<>();

      // url参数拼接


      URL realUrl = new URL(urlFyInfo);
      HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
      conn.setConnectTimeout(5000);
      conn.setReadTimeout(5000);
      conn.setRequestMethod(method);

      // request headers
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        conn.setRequestProperty(entry.getKey(), entry.getValue());
      }

      // request body
      Map<String, Boolean> methods = new HashMap<>();
      methods.put("POST", true);
      methods.put("PUT", true);
      methods.put("PATCH", true);
      Boolean hasBody = methods.get(method);
      if (hasBody != null) {
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(urlencode(bodyParams));
        out.flush();
        out.close();
      }

      // 定义 BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;

      while ((line = in.readLine()) != null) {
        result.append(line);
      }


    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result.toString();
  }
}