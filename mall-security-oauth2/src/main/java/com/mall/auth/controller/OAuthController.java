package com.mall.auth.controller;

import com.mall.base.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description spring cloud oauth2默认的接口，该类用于重写这些接口，以达到重新封装数据的目的
 * @Date 2021/5/16 11:05
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "oauth")
public class OAuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostMapping(value = "/token")
    public CommonResult postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return CommonResult.success(oAuth2AccessToken);
    }
}
