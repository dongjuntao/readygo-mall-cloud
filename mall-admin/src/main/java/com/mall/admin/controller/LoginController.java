package com.mall.admin.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.mall.admin.service.AdminUserService;
import com.mall.base.CommonResult;
import com.mall.base.enums.ResultCodeEnum;
import com.mall.base.vo.LoginVO;
import com.mall.generator.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/26 14:29
 * @Version 1.0
 */
@RestController(value = "/user")
public class LoginController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/login")
    public CommonResult get(@RequestBody LoginVO loginVO,
                             @RequestParam("captchaVerification") String captchaVerification) {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        //登录时滑动验证码二次验证
        ResponseModel response = captchaService.verification(captchaVO);
        if(!response.isSuccess()){
            //校验失败，返回到前端
            return CommonResult.fail(response.getRepCode(), response.getRepMsg());
        }
        List<AdminUser> adminUser = adminUserService.getAdminUserByUserName(loginVO.getUserName());
        if (adminUser!= null && !adminUser.get(0).getPassword().equals(loginVO.getPassword())) {
            //校验失败，返回到前端
            return CommonResult.fail(ResultCodeEnum.VALIDATE_FAILED.getCode(),ResultCodeEnum.VALIDATE_FAILED.getMessage());
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), adminUser);
    }
}
