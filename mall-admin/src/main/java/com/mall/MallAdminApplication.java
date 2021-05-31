package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author DongJunTao
 * @Description 商城管理端后端启动类
 * @Date 2021/4/26 14:50
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.mall.admin.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class MallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
    }
}
