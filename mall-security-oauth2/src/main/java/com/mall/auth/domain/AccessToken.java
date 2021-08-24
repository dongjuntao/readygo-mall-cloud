package com.mall.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * @Author DongJunTao
 * @Description 封装返回的token信息
 * @Date 2021/6/9 10:54
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    /**
     * token值
     */
    private String accessToken;
    /**
     * token类型
     */
    private String tokenType;
    /**
     * 刷新token值
     */
    private String refreshToken;
    /**
     * token有效期
     */
    private int expiresIn;
    /**
     * scope
     */
    private Set<String> scope;
    /**
     * token增强信息（用户的基本信息）
     */
    Map<String, Object>  enhanceInfo;

}
