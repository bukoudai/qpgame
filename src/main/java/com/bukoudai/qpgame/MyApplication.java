package com.bukoudai.qpgame;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * @author bukoudai
 */
@SpringBootApplication
@EnableCaching
@EnableSwagger2
@MapperScan("com.bukoudai.qpgame.mapper")
@EnableScheduling
@Slf4j
public class MyApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyApplication.class, args);
    String name = ManagementFactory.getRuntimeMXBean().getName();

    log.info(name);

// get pid

    String pid = name.split("@")[0];

    log.info("项目启动 Pid is:" + pid);
    try {
      File pidFile = new File("pid");
      if (!pidFile.exists()) {
        pidFile.createNewFile();
      }
      FileUtil.writeUtf8String(pid, pidFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      File pidFile = new File("pid");
      if (pidFile.exists()) {
        pidFile.delete();
      }
    }));
  }

}
