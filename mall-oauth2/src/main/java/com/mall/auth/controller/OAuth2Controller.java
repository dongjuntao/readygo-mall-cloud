package com.mall.auth.controller;

import com.mall.admin.api.feign.AdminUserService;
import com.mall.base.CommonResult;
import com.mall.base.dto.UserDTO;
import com.mall.base.enums.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 登录认证成功后返回token
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/token")
    public CommonResult get(@RequestParam("userName") String userName,
                            @RequestParam("password") String password) {
        UserDTO user = adminUserService.getAdminUserByUserName(userName);

        if (user!= null && !passwordEncoder.matches(password,user.getPassword())) {
            //校验失败，返回到前端
            return CommonResult.fail(ResultCodeEnum.VALIDATE_FAILED.getCode(),ResultCodeEnum.VALIDATE_FAILED.getMessage());
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), user);
    }
}
