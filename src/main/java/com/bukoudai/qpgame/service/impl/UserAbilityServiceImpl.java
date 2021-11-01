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

  @Override
  public void openOrCloseAutoTranslation(long senderId, boolean open) {
    UserAbility userAbility = baseMapper.selectOne(new QueryWrapper<>(UserAbility.builder().loginNo(senderId).build()));
    boolean saveFlag = userAbility == null;

    if (open) {
      if (saveFlag) {
        UserAbility build = UserAbility.builder().loginNo(senderId).autoTranslation(1).build();
        baseMapper.insert(build);
      } else {
        userAbility.setAutoTranslation(1);
        baseMapper.updateById(userAbility);
      }
    } else {

      if (saveFlag) {
        UserAbility build = UserAbility.builder().loginNo(senderId).autoTranslation(0).build();
        baseMapper.insert(build);
      } else {
        userAbility.setAutoTranslation(0);
        baseMapper.updateById(userAbility);
      }
    }

  }
}
