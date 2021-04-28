package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author DongJunTao
 * @Description 商城管理端后端启动类
 * @Date 2021/4/26 14:50
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.mall.generator")
public class MallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
    }
}
