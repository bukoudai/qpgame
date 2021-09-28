package com.bukoudai.qpgame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bukoudai.qpgame.entitys.Jokes;
import org.apache.ibatis.annotations.Select;

public interface JokesMapper extends BaseMapper<Jokes> {
    @Select("SELECT * FROM `jokes` WHERE type=#{type} limit #{limit},1")
    Jokes randomOneByType(Integer type, Integer limit);
}
