package com.mall.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.mall.admin.service.AdminUserService;
import com.mall.auth.api.feign.OAuthService;
import com.mall.base.CommonResult;
import com.mall.base.dto.UserDTO;
import com.mall.base.vo.LoginVO;
import com.mall.generator.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/26 14:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "user")
public class AdminUserController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private OAuthService oAuthService;


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
        String result = oAuthService.get(loginVO.getUserName(),loginVO.getPassword());
        JSONObject jsonResult = JSON.parseObject(result);
        if (!"200".equals(jsonResult.get("code"))) {
            //校验失败，返回到前端
            return CommonResult.fail(String.valueOf(jsonResult.get("code")), String.valueOf(jsonResult.get("message")));
        }
        return CommonResult.success(String.valueOf(jsonResult.get("code")), String.valueOf(jsonResult.get("message")), null);
    }

    @GetMapping("/getAdminUserById")
    public UserDTO getAdminUserById(@RequestParam long id) {
        AdminUser adminUser = adminUserService.getAdminUserById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(adminUser.getUserName());
        userDTO.setPassword(adminUser.getPassword());
        return userDTO;
    }

    @GetMapping("/getAdminUserByUserName")
    public UserDTO getAdminUserByUserName(@RequestParam String userName) {
        List<AdminUser> adminUserList = adminUserService.getAdminUserByUserName(userName);
        if (adminUserList == null || adminUserList.size() == 0) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(adminUserList.get(0).getUserName());
        userDTO.setPassword(adminUserList.get(0).getPassword());
        return userDTO;
    }
}
