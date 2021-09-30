package com.bukoudai.qpgame;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
    log.info("项目启动");
  }

}
