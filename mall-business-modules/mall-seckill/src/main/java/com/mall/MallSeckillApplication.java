package com.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author DongJunTao
 * @Description 优惠券微服务启动类
 * @Date 2022/01/05 21:48
 * @Version 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MallSeckillApplication {
    public static void main( String[] args ) {
        SpringApplication.run(MallSeckillApplication.class, args);
    }
}
