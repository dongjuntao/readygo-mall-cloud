package com.mall.auth.handler;

import com.alibaba.fastjson.JSON;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/4 15:22
 * @Version 1.0
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        CommonResult result = CommonResult
                .fail(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage());;
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
