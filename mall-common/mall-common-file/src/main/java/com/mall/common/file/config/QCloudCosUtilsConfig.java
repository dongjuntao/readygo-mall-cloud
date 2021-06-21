package com.mall.common.file.config;

import com.mall.common.file.util.QCloudCosUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author DongJunTao
 * @Description 腾讯云cos配置类
 * @Date 2021/6/16 9:28
 * @Version 1.0
 */
@Configuration
public class QCloudCosUtilsConfig {

    @Bean
    public QCloudCosUtils QCloudCosUtils() {
        return new QCloudCosUtils();
    }
}
