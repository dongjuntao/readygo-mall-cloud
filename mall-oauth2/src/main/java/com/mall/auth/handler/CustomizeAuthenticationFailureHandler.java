package com.mall.auth.handler;

import com.alibaba.fastjson.JSON;
import com.mall.base.CommonResult;
import com.mall.base.enums.ResultCodeEnum;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author DongJunTao
 * @Description 登录失败后处逻辑
 * @Date 2021/5/12 15:15
 * @Version 1.0
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        CommonResult result = null;
        if (e instanceof AccountExpiredException) {
            //账号过期
            result = CommonResult.fail(ResultCodeEnum.USER_ACCOUNT_EXPIRED.getCode(), ResultCodeEnum.USER_ACCOUNT_EXPIRED.getMessage());
        } else if (e instanceof BadCredentialsException) {
            //密码错误
            result = CommonResult.fail(ResultCodeEnum.USER_CREDENTIALS_ERROR.getCode(),ResultCodeEnum.USER_CREDENTIALS_ERROR.getMessage());
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = CommonResult.fail(ResultCodeEnum.USER_CREDENTIALS_EXPIRED.getCode(),ResultCodeEnum.USER_CREDENTIALS_EXPIRED.getMessage());
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = CommonResult.fail(ResultCodeEnum.USER_ACCOUNT_DISABLE.getCode(),ResultCodeEnum.USER_ACCOUNT_DISABLE.getMessage());
        } else if (e instanceof LockedException) {
            //账号锁定
            result = CommonResult.fail(ResultCodeEnum.USER_ACCOUNT_LOCKED.getCode(),ResultCodeEnum.USER_ACCOUNT_LOCKED.getMessage());
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = CommonResult.fail(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getMessage());
        }else{
            //其他错误
            result = CommonResult.fail(ResultCodeEnum.COMMON_FAIL.getCode(),ResultCodeEnum.COMMON_FAIL.getMessage());
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }

}
