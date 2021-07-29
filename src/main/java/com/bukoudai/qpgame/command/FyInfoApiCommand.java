package com.bukoudai.qpgame.command;

import cn.hutool.json.JSONObject;
import com.bukoudai.qpgame.thirdapi.FyInfo;
import lombok.AllArgsConstructor;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FyInfoApiCommand implements Command {


    @Override
    public String execute(GroupMessageEvent event, long botId) {
        String s = event.getMessage().contentToString();
        String[] s1 = s.split(" ");
        String provinceName = "四川省";
        String city = "成都";
        if (s1.length > 3) {
            provinceName = s1[1];
            city = s1[2];
        }

        StringBuilder msgB = new StringBuilder("今日疫情信息:\r\n");

        JSONObject fyCityDtail = FyInfo.getFyCityDtail(provinceName, city);
        msgB.append(fyCityDtail == null ? "" : fyCityDtail.toStringPretty());


        return msgB.toString();

    }
}
