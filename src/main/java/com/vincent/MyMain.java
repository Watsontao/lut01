package com.vincent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@MapperScan("user.mapper")*/
public class MyMain {
    public static void main(String[] args) {
        SpringApplication.run(MyMain.class,args);
    }
}
