package com.mall.auth.controller;

import com.mall.base.CommonResult;
import com.mall.base.enums.ResultCodeEnum;
import com.mall.base.vo.LoginVO;
import com.mall.generator.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

//    @Autowired
//    private AdminUserService adminUserService;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @PostMapping("/auth")
//    public CommonResult get(@RequestBody LoginVO loginVO) {
//        //登录时滑动验证码二次验证
//        List<AdminUser> adminUser = adminUserService.getAdminUserByUserName(loginVO.getUserName());
//        if (adminUser!= null && passwordEncoder.matches(loginVO.getPassword(),adminUser.get(0).getPassword())) {
//            //校验失败，返回到前端
//            return CommonResult.fail(ResultCodeEnum.VALIDATE_FAILED.getCode(),ResultCodeEnum.VALIDATE_FAILED.getMessage());
//        }
//        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUser);
//    }
}
