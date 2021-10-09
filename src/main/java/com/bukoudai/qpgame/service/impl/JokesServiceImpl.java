package com.bukoudai.qpgame.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesStatusEnum;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.mapper.JokesMapper;
import com.bukoudai.qpgame.service.JokesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JokesServiceImpl extends ServiceImpl<JokesMapper, Jokes> implements JokesService {


  @Override
  public Jokes randomOneByType(JokesTypeEnum type) {
    Integer typeCode = type.getCode();
    QueryWrapper<Jokes> wrapper = new QueryWrapper<Jokes>().eq("type", typeCode).eq("status", JokesStatusEnum.UNUSED.getCode());
    Integer integer = baseMapper.selectCount(wrapper);
    Jokes jokes;
    if (integer.equals(0)) {
      jokes = new Jokes();
      jokes.setText("没有存货了");

    }
    int i = RandomUtil.randomInt(integer);
    jokes = baseMapper.randomOneByType(typeCode, i);
    jokes.setStatus(JokesStatusEnum.USED.getCode());
    baseMapper.updateById(jokes);

    return jokes;

  }
}
