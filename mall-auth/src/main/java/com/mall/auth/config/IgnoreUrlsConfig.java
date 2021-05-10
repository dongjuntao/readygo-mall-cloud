package com.mall.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 用于配置需要忽略的白名单地址
 * @Date 2021/5/8 15:12
 * @Version 1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
