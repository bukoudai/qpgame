package com.bukoudai.qpgame.thirdapi.translateapi;


import com.bukoudai.qpgame.enums.TransLateLangEnum;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.tmt.v20180321.models.LanguageDetectRequest;
import com.tencentcloudapi.tmt.v20180321.models.LanguageDetectResponse;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TranslateApiService {


    public static void main(String[] args) {
        String text = "翻译";

        String s = TranslateApiService.textTranslate(text, null, TransLateLangEnum.en);
        System.out.println(s);
    }

    public static String languageDetect(String text) {
        String reText;

        reText = TranslateApiProcess.doJob(tmtClient -> {
            try {

                LanguageDetectRequest req = new LanguageDetectRequest();
                req.setText(text);
                req.setProjectId(0L);
                // 返回的resp是一个LanguageDetectResponse的实例，与请求对象对应
                LanguageDetectResponse resp = tmtClient.LanguageDetect(req);
                // 输出json格式的字符串回包
                return resp.getLang();
            } catch (TencentCloudSDKException e) {
                log.error(e.getMessage());
            }
            return null;
        });


        return reText;
    }

    public static String textTranslate(String text, String source, TransLateLangEnum targetEnum) {
        String reText;
        if (StringUtils.isBlank(source)) {
            source = "auto";
        }
        String finalSource = source;
        reText = TranslateApiProcess.doJob(tmtClient -> {
            try {
                TextTranslateRequest req = new TextTranslateRequest();
                req.setSourceText(text);
                req.setSource(finalSource);
                req.setTarget(targetEnum.getCode());
                req.setProjectId(TranslateApiProcess.appid);
                // 返回的resp是一个TextTranslateResponse的实例，与请求对象对应
                TextTranslateResponse resp = tmtClient.TextTranslate(req);
                // 输出json格式的字符串回包
                return resp.getTargetText();
            } catch (TencentCloudSDKException e) {
                log.error(e.getMessage());
            }
            return "翻译失败";
        });


        return reText;
    }


}
