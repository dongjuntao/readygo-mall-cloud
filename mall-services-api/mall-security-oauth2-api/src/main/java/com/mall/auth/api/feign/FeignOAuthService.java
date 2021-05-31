package com.mall.auth.api.feign;

import com.mall.base.CommonResult;
import com.mall.base.config.FeignConfig;
import com.mall.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description spring cloud oauth2默认的接口,重写后通过openfeign提供给其他组价调用
 * @Date 2021/5/16 17:17
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_SECURITY_OAUTH2,configuration = FeignConfig.class)
@RequestMapping(value = "/oauth")
public interface FeignOAuthService {

    /**
     * 重写/oauth/token接口，获取token
     * @param parameters
     * @return
     */
    @PostMapping("/token")
    CommonResult postAccessToken(@RequestParam Map<String, String> parameters);
}
