package com.vincent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling  //启用定时任务功能
@SpringBootApplication

public class MyMain {
    public static void main(String[] args) {
        SpringApplication.run(MyMain.class,args);
    }
}
