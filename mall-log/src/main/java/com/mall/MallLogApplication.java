package com.mall;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 日志服务启动类
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableRabbit
public class MallLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallLogApplication.class, args);
    }

}
