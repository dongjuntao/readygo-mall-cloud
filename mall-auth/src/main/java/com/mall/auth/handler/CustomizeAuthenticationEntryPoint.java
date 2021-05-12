package com.mall.auth.handler;

import com.alibaba.fastjson.JSON;
import com.mall.base.CommonResult;
import com.mall.base.enums.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author DongJunTao
 * @Description 未登录时返回的异常情况
 * @Date 2021/512 10:36
 * @Version 1.0
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        CommonResult result = CommonResult.fail(ResultCodeEnum.UNAUTHORIZED.getCode(),ResultCodeEnum.UNAUTHORIZED.getMessage());
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
