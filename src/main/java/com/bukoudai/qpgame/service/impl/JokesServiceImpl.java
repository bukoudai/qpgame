package com.bukoudai.qpgame.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.Jokes;
import com.bukoudai.qpgame.enums.JokesStatusEnum;
import com.bukoudai.qpgame.enums.JokesTypeEnum;
import com.bukoudai.qpgame.enums.TianApiEnum;
import com.bukoudai.qpgame.mapper.JokesMapper;
import com.bukoudai.qpgame.service.JokesService;
import com.bukoudai.qpgame.thirdapi.tianapi.TianapiProcess;
import com.bukoudai.qpgame.vo.TianApiResVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class JokesServiceImpl extends ServiceImpl<JokesMapper, Jokes> implements JokesService {


  @Override
  public Jokes randomOneByType(JokesTypeEnum type, boolean logUsed) {
    Integer typeCode = type.getCode();
    QueryWrapper<Jokes> wrapper = new QueryWrapper<Jokes>().eq("type", typeCode).eq("status", JokesStatusEnum.UNUSED.getCode());
    Integer integer = baseMapper.selectCount(wrapper);
    Jokes jokes;
    if (integer.equals(0)) {
      //自己存的没有了 则从接口获取
      jokes = new Jokes();

      JokesTypeEnum byWeek = JokesTypeEnum.getByWeek();
      TianApiEnum tianApiEnum = TianApiEnum.parse(byWeek.getCode());
      log.info(" url {}: ", tianApiEnum.getUrl());
      String execute = TianapiProcess.execute(tianApiEnum);
      log.info(" res:{}: ", execute);
      TianApiResVo<JSONObject> tianZMSCVo = JSONUtil.toBean(execute, TianApiResVo.class);

      String content = tianZMSCVo.getNewslist().get(0).getStr("content");
      jokes.setText(content);
      jokes.setType(tianApiEnum.getCode());
      jokes.setStatus(JokesStatusEnum.USED.getCode());
      baseMapper.insert(jokes);
    } else {
      int i = RandomUtil.randomInt(integer);
      jokes = baseMapper.randomOneByType(typeCode, JokesStatusEnum.UNUSED.getCode(), i);
    }

    if (logUsed) {
      jokes.setStatus(JokesStatusEnum.USED.getCode());
      baseMapper.updateById(jokes);

    }

    return jokes;

  }

  @Override
  public List<Jokes> queryListByType(JokesTypeEnum type) {
    Integer typeCode = type.getCode();
    QueryWrapper<Jokes> wrapper = new QueryWrapper<Jokes>().eq("type", typeCode).eq("status", JokesStatusEnum.UNUSED.getCode());
    return baseMapper.selectList(wrapper);
  }
}
