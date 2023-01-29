package com.bukoudai.qpgame.command.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bukoudai.qpgame.command.Command;
import com.bukoudai.qpgame.entitys.UserPoints;
import com.bukoudai.qpgame.entitys.UserTextContentLog;
import com.bukoudai.qpgame.entitys.UserTextContentPic;
import com.bukoudai.qpgame.mapper.UserPointsMapper;
import com.bukoudai.qpgame.mapper.UserTextContentLogMapper;
import com.bukoudai.qpgame.service.UserAbilityService;
import com.bukoudai.qpgame.service.UserTextContentPicService;
import com.bukoudai.qpgame.thirdapi.cosapi.CosApiProcess;
import com.bukoudai.qpgame.thirdapi.cosapi.UploadFileResult;
import com.bukoudai.qpgame.thirdapi.translateapi.TranslateApiService;
import com.bukoudai.qpgame.vo.SendMsgVo;
import com.qcloud.cos.model.UploadResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.FileSupported;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.RemoteFiles;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * 添加积分命令
 */
@Slf4j
@Service
@AllArgsConstructor
public class AddPointCommand implements Command {

    private final UserPointsMapper userPointsMapper;
    private final UserTextContentLogMapper userTextContentLogMapper;
    private final UserAbilityService userAbilityService;
    private final CosApiProcess cosApiProcess;
    private final UserTextContentPicService userTextContentPicService;


    @Override
    public SendMsgVo execute(MessageEvent event, long botId) {
        User sender = event.getSender();
        long senderId = sender.getId();
        String nick = sender.getNick();
        //增加积分
        UserPoints userPoints = userPointsMapper.selectOne(new QueryWrapper<>(UserPoints.builder().loginNo(senderId).build()));
        if (userPoints == null) {
            userPointsMapper.insert(UserPoints.builder().loginNo(senderId).points(1).build());
        } else {
            userPoints.setPoints(userPoints.getPoints() + 1);
            userPointsMapper.updateById(userPoints);
        }


        //记录日志
        String text = logMsgInfo(event, senderId, nick);
        //判断是否开启实时翻译
        if (userAbilityService.checkAutoTranslation(senderId)) {
            if (userPoints == null || (userPoints.getPoints() - TranslateApiCommand.ONE_CONSUME_POINTS < 0)) {
                return null;
            }
            userPoints.setPoints(userPoints.getPoints() - TranslateApiCommand.ONE_CONSUME_POINTS);
            userPointsMapper.updateById(userPoints);
            return new SendMsgVo(TranslateApiService.autoTextTranslate(text), false);
        }
        //未开启 则判断是否非中文

        return null;
    }

    /**
     * 记录日志
     *
     * @param event    事件
     * @param senderId
     * @param nick
     * @return
     */
    @NotNull
    private String logMsgInfo(MessageEvent event, long senderId, String nick) {
        String text = event.getMessage().contentToString();
        UserTextContentLog.UserTextContentLogBuilder logBuilder = UserTextContentLog.builder()
                .creatTime(DateUtil.format(new Date(), "HHmmss"))
                .creatDay(DateUtil.format(new Date(), "yyyyMMdd"))
                .textContent(text)
                .user(String.valueOf(senderId)).nick(nick);
        UserTextContentLog textContentLog = logBuilder.build();
        userTextContentLogMapper.insert(textContentLog);
        MessageChain message = event.getMessage();
        for (SingleMessage singleMessage : message) {
            if (singleMessage instanceof Image || singleMessage instanceof FlashImage || singleMessage instanceof FileMessage) {
                String imageUrl;
                String picName;
                Image quoteReply;
                //根据类型处理图片及文件
                if (singleMessage instanceof Image) {
                    quoteReply = (Image) singleMessage;
                    imageUrl = Image.queryUrl(quoteReply);
                    picName = quoteReply.getImageId().replace("{", "").replace("}", "");
                } else if (singleMessage instanceof FlashImage) {
                    quoteReply = ((FlashImage) singleMessage).getImage();
                    imageUrl = Image.queryUrl(quoteReply);
                    picName = quoteReply.getImageId().replace("{", "").replace("}", "");
                } else {
                    FileMessage fileMessage = (FileMessage) singleMessage;
                    Contact eventSubject = event.getSubject();
                    AbsoluteFile absoluteFile = fileMessage.toAbsoluteFile((Group) eventSubject);
                    if (absoluteFile != null) {
                        imageUrl = absoluteFile.getUrl();
                        picName = absoluteFile.getName();
                    } else {
                        continue;
                    }
                }
                if (imageUrl == null) {
                    continue;
                }


                //图片地址转换为流
                byte[] bytes = HttpUtil.downloadBytes(imageUrl);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                UploadFileResult uploadResult = cosApiProcess.uploadFile(inputStream, picName);
                if (uploadResult.getIsSuccess()) {
                    UserTextContentPic userTextContentPic = new UserTextContentPic();
                    userTextContentPic.setUrl(uploadResult.getUrl());
                    userTextContentPic.setUserTextContentLogId(textContentLog.getId());
                    userTextContentPic.setBucketName(uploadResult.getBucketName());
                    userTextContentPic.setPicName(picName);
                    userTextContentPic.setBucketKey(uploadResult.getBucketKey());
                    userTextContentPicService.save(userTextContentPic);
                }
            }
        }
        return text;
    }

    @Override
    public String help() {
        return null;
    }
}
