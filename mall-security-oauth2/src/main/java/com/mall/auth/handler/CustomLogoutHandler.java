package com.mall.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author DongJunTao
 * @Description 自定义登出处理逻辑
 * @Date 2021/6/4 15:13
 * @Version 1.0
 */
@Component
@FrameworkEndpoint
public class CustomLogoutHandler implements LogoutHandler {

    ConsumerTokenServices consumerTokenServices = null;

    @Override
    public void logout(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       Authentication authentication) {
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(httpServletRequest.getServletContext());
        consumerTokenServices = (ConsumerTokenServices) applicationContext.getBean("consumerTokenServices");
        //删除redis中的token
        System.out.println("to == " + httpServletRequest.getParameter("token"));
        consumerTokenServices.revokeToken(httpServletRequest.getParameter("token"));
    }
}
