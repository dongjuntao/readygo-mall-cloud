package com.mall.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.mall.admin.service.AdminUserService;
import com.mall.auth.api.feign.FeignLoginService;
import com.mall.auth.api.feign.FeignOAuthService;
import com.mall.base.CommonResult;
import com.mall.base.constant.OAuth2Constant;
import com.mall.base.dto.UserDTO;
import com.mall.base.vo.LoginVO;
import com.mall.generator.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/26 14:29
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/user")
public class AdminUserController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private FeignOAuthService feignOAuthService;
    @Autowired
    private FeignLoginService feignLoginService;

    /**
     * 登录后从认证中心获取token
     * @param loginVO
     * @param captchaVerification
     * @return
     */
    @PostMapping("/login")
    public CommonResult postAccessToken(@RequestBody LoginVO loginVO,
                             @RequestParam("captchaVerification") String captchaVerification) {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        //登录时滑动验证码二次验证
        ResponseModel response = captchaService.verification(captchaVO);
        if(!response.isSuccess()){
            //校验失败，返回到前端
            return CommonResult.fail(response.getRepCode(), response.getRepMsg());
        }
        //先认证
        String result = feignLoginService.login(loginVO.getUserName(),loginVO.getPassword());
        JSONObject jsonResult = JSON.parseObject(result);
        if (!"200".equals(jsonResult.get("code"))) {
            //校验失败，返回到前端
            return CommonResult.fail(String.valueOf(jsonResult.get("code")), String.valueOf(jsonResult.get("message")));
        }
        //获取token
        Map<String, String> params = new HashMap<>();
        params.put("username", loginVO.getUserName());
        params.put("password", loginVO.getPassword());
        params.put("grant_type", OAuth2Constant.GRANT_TYPE_PASSWORD);
        params.put("scope", "all");
        params.put("client_id", OAuth2Constant.ADMIN_CLIENT_ID);
        params.put("client_secret", OAuth2Constant.ADMIN_CLIENT_SECRET);

        CommonResult commonResult = feignOAuthService.postAccessToken(params);

        return commonResult;
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
