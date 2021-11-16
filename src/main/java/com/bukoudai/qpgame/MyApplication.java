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
import java.nio.file.Files;

import static cn.hutool.core.util.StrUtil.format;

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
  static String pidshFileName = "killpid.sh";

  public static void main(String[] args) {
    SpringApplication.run(MyApplication.class, args);
    createPidFile();
  }

  private static void createPidFile() {
    String name = ManagementFactory.getRuntimeMXBean().getName();
    log.info(name);
// get pid
    String pid = name.split("@")[0];
    log.info("项目启动 Pid is:{}", pid);
    try {
      File pidFile = new File(pidshFileName);
      if (!pidFile.exists() && Boolean.FALSE.equals(pidFile.createNewFile())) {
        log.error("项目启动 {}文件创建失败 ", pidshFileName);
      }
      FileUtil.writeUtf8String(format("kill {} ", pid), pidFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    //关闭项目删除sh文件
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      File pidFile = new File(pidshFileName);
      boolean delete = false;
      try {
        delete = Files.deleteIfExists(pidFile.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (Boolean.FALSE.equals(delete)) {
        log.error("项目关闭 删除{}文件失败 ", pidshFileName);
      }
    }));
  }

}
