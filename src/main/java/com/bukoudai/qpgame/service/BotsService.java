package com.bukoudai.qpgame.service;

import com.bukoudai.qpgame.entitys.Bots;
import net.mamoe.mirai.Bot;

public interface BotsService {

  Bots getDefaultBot();

  Bot loginBot(Bots bots);

  Bot loginBot();
}
