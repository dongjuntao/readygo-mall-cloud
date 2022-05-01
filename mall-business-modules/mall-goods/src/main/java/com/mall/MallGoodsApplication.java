package com.mall;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author DongJunTao
 * @Description 商品管理启动类
 * @Date 2021/4/26 14:50
 * @Version 1.0
 */
@SpringBootApplication
@EnableRabbit
public class MallGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class, args);
    }
}
