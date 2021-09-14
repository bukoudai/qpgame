package com.bukoudai.qpgame.command.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.thirdapi.FyInfo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.MessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FyInfoApiCommand implements Command {


    @Override
    public String execute(MessageEvent event, long botId) {
        String s = event.getMessage().contentToString();
        String s2 = s.replaceFirst(" ", SPLIS_WORDS);

        String[] s1 = s2.split(SPLIS_WORDS);
        String provinceName = "四川省";
        String city = "成都";

        if (s1.length > 1) {
            provinceName = s1[1];
            if (s1.length > 2) {
                city = s1[2];
            }
        }
        StringBuilder msgB = new StringBuilder();
        if ("新闻".equals(provinceName)) {
            msgB.append("今日疫情新闻:\r\n");
            JSONArray fyNewsJSONArray = FyInfo.getFyNewsJSONArray(Boolean.TRUE);
            msgB.append(fyNewsJSONArray.toStringPretty());

        } else {
            msgB.append("今日疫情信息:\r\n");
            JSONObject fyCityDtail = FyInfo.getFyCityDtail(provinceName, city);
            msgB.append(fyCityDtail == null ? "" : fyCityDtail.toStringPretty());
        }


        return msgB.toString();

    }

    @Override
    public String help() {
        return null;
    }
}
