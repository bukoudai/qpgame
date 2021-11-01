package com.bukoudai.qpgame.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bukoudai.qpgame.entitys.UserAbility;

public interface UserAbilityService extends IService<UserAbility> {
  /**
   * 判断是否开启自动翻译功能
   *
   * @param senderId
   * @return
   */
  public boolean checkAutoTranslation(long senderId);

  public void openOrCloseAutoTranslation(long senderId, boolean open);
}
