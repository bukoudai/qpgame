package com.bukoudai.qpgame.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bukoudai.qpgame.entitys.UserAbility;
import com.bukoudai.qpgame.enums.AutoTranslationEnum;
import com.bukoudai.qpgame.mapper.UserAbilityMapper;
import com.bukoudai.qpgame.service.UserAbilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAbilityServiceImpl extends ServiceImpl<UserAbilityMapper, UserAbility> implements UserAbilityService {


  @Override
  public boolean checkAutoTranslation(long senderId) {
    UserAbility userAbility = baseMapper.selectOne(new QueryWrapper<>(UserAbility.builder().loginNo(senderId).build()));
    if (userAbility == null) {
      return false;
    }
    return AutoTranslationEnum.USED.equals(AutoTranslationEnum.parse(userAbility.getAutoTranslation()));
  }
}
