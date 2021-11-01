package com.bukoudai.qpgame.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandConfig {


  public static String LQSAY_COMMAND_KEY;
  public static String LQSAY_COMMAND_KEY_HELPMSG;
  public static String LQSAY_COMMAND_KEY_SUCCESSMSG;
  public static String LQSAY_COMMAND_KEY_LABEL;


  @Value("${command.key.lqsay.key}")
  public void setLqsayCommandKey(String commandKey) {
    LQSAY_COMMAND_KEY = commandKey;
  }

  @Value("${command.key.lqsay.helpmsg}")
  public void setLqsayCommandKeyHelpmsg(String commandKey) {
    LQSAY_COMMAND_KEY_HELPMSG = commandKey;
  }

  @Value("${command.key.lqsay.successmsg}")
  public void setLqsayCommandKeySuccessmsg(String commandKey) {
    LQSAY_COMMAND_KEY_SUCCESSMSG = commandKey;
  }

  @Value("${command.key.lqsay.label}")
  public void setLqsayCommandKeyLabel(String commandKey) {
    LQSAY_COMMAND_KEY_LABEL = commandKey;
  }

}
