package com.bukoudai.qpgame.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesTypeEnum;


public interface JokesService extends IService<Jokes> {
  /**
   * 获取一条随机记录
   *
   * @param type    记录类型
   * @param logUsed 是否更新为已使用
   * @return
   */
  Jokes randomOneByType(JokesTypeEnum type, boolean logUsed);
}
