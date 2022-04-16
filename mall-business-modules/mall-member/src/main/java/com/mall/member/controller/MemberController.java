package com.mall.member.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.mall.auth.api.feign.FeignLoginService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.OAuth2Constant;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.vo.LoginVO;
import com.mall.member.entity.MemberEntity;
import com.mall.member.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 会员controller
 * @Date 2022/4/14 20:48
 * @Version 1.0
 */
@RestController
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private FeignLoginService feignLoginService;

    /**
     * 会员注册
     */
    @PostMapping("register")
    public CommonResult register(@RequestBody MemberEntity memberEntity){
        int num = memberService.register(memberEntity);
        if (num == -1){
            return CommonResult.fail(ResultCodeEnum.USER_NAME_EXIST.getCode(),ResultCodeEnum.USER_NAME_EXIST.getMessage());
        }
        return CommonResult.success();
    }


    /**
     * 登录后从认证中心获取token
     * @param loginVO
     * @param captchaVerification
     * @return
     */
    @PostMapping("login")
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
        //认证，获取token
        String resultMap = feignLoginService.login(loginVO.getUserName(),loginVO.getPassword(),
                loginVO.getUserType(), OAuth2Constant.ADMIN_CLIENT_ID);
        CommonResult result = JSON.parseObject(resultMap, CommonResult.class);
        if (StringUtils.isEmpty(resultMap) || result == null) {
            return CommonResult.fail();
        }
        return result;
    }

    @DeleteMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return CommonResult.fail();
        }
        String result = feignLoginService.loginOut(token.replace("Bearer ", ""));
        JSONObject jsonResult = JSON.parseObject(result);
        if (!"200".equals(jsonResult.get("code"))) {
            //校验失败，返回到前端
            return CommonResult.fail(String.valueOf(jsonResult.get("code")), String.valueOf(jsonResult.get("message")));
        }
        return CommonResult.success();
    }

    /**
     * 根据用户名查看会员信息
     * @param params
     * @return
     */
    @GetMapping("getMemberByParams")
    public CommonResult getUserByParams(@RequestParam Map<String, Object> params) {
        MemberEntity member = memberService.getMemberByParams(params);
        if (member == null) {
            return null;
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), member);
    }
}
