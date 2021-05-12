package com.mall.admin.openfeign;

import com.mall.base.vo.LoginVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mall-auth")
public interface OAuthService {

    @PostMapping("/oauth2/auth")
    String get(@RequestParam("userName") String userName, @RequestParam("password") String password);
}
