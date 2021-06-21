package com.mall.common.file.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author DongJunTao
 * @Description 腾讯云对象存储cos相关配置信息
 * @Date 2021/6/16 9:24
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.qcloud")
public class QCloudCosProperty {
    //API密钥secretId
    private String secretId;
    //API密钥secretKey
    private String secretKey;
    //存储桶所属地域
    private String region;
    //存储桶空间名称
    private String bucketName;
    //存储桶访问域名
    private String url;
    //上传文件前缀路径(eg:/images/)
    private String prefix;
}
