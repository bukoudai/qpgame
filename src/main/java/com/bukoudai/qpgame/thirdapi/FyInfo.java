package com.bukoudai.qpgame.thirdapi;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Encoder;

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


public class FyInfo {
    //云市场分配的密钥Id
    @Value("${yysj.secret_id}")
    private static String secretId = "AKIDJcTn5aHuf5Dn0ugI1oe1mb1nNtn4qnt1H34U";
    //云市场分配的密钥Key
    @Value("${yysj.secret_key}")
    private static String secretKey = "472hGPtyTZx7LLX7hruDFV57RnNqV54J190BShE";
    private static String source = "market";

    @Value("${yysj.fy_news_url}")
    private static String fy_news_url = "http://service-3k8gkc1x-1255468759.sh.apigw.tencentcs.com/release/fy_news";
    @Value("${yysj.fy_details}")
    private static String fy_details = "http://service-3k8gkc1x-1255468759.sh.apigw.tencentcs.com/release/fy_details";

    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
        String sig = new BASE64Encoder().encode(hash);

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
                        jsonObject.set("updateTime",provinceDtail.get("updateTime"));
                        jsonObject.set("todayStatictic",provinceDtail.get("todayStatictic"));
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
                        jsonObject.set("updateTime",resBody.get("updateTime"));
                        jsonObject.set("todayStatictic",resBody.get("todayStatictic"));
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

    private static String getFyInfo(String urlFyInfo) {


        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            Calendar cd = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String datetime = sdf.format(cd.getTime());
            // 签名
            String auth = calcAuthorization(source, secretId, secretKey, datetime);
            // 请求方法
            String method = "GET";
            // 请求头
            Map<String, String> headers = new HashMap<>();
            headers.put("X-Source", source);
            headers.put("X-Date", datetime);
            headers.put("Authorization", auth);

            // 查询参数
            Map<String, String> queryParams = new HashMap<>();

            // body参数
            Map<String, String> bodyParams = new HashMap<>();

            // url参数拼接
            String url = urlFyInfo;
            if (!queryParams.isEmpty()) {
                url += "?" + urlencode(queryParams);
            }


            URL realUrl = new URL(url);
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