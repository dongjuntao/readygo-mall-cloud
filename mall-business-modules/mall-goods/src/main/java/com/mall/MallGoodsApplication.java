package com.mall;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author DongJunTao
 * @Description 商品管理启动类
 * @Date 2021/4/26 14:50
 * @Version 1.0
 */
@SpringBootApplication
@EnableRabbit
@EnableAutoDataSourceProxy
public class MallGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class, args);
    }
}
