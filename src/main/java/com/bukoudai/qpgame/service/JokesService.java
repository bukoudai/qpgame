package com.bukoudai.qpgame.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesTypeEnum;


public interface JokesService extends IService<Jokes> {

    Jokes randomOneByType(JokesTypeEnum type);
}
