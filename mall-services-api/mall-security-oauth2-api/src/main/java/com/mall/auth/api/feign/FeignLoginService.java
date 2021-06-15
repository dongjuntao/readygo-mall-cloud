package com.mall.auth.api.feign;

import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author DongJunTao
 * @Description 登录认证
 * @Date 2021/5/16 17:17
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_SECURITY_OAUTH2,configuration = FeignConfig.class)
@RequestMapping(value = "auth")
public interface FeignLoginService {
    /**
     * 身份认证（security配置loginProcessingUrl地址，用于访问时进行身份认证）
     * @param userName
     * @param password
     * @param password
     * @return
     */
    @PostMapping("/login")
    String login(@RequestParam("userName") String userName,
                 @RequestParam("password") String password,
                 @RequestParam("clientId") String clientId);

    @DeleteMapping("/logout")
    String loginOut(@RequestParam("token") String token);

}
