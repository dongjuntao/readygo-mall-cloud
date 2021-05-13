package com.mall.auth.api.feign;

import com.mall.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ServiceNameConstant.MALL_OAUTH2)
public interface OAuthService {

    @PostMapping("/oauth2/token")
    String get(@RequestParam("userName") String userName, @RequestParam("password") String password);
}
