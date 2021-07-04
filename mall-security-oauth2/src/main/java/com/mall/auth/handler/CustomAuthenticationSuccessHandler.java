package com.mall.auth.handler;

import com.alibaba.fastjson.JSON;
import com.mall.auth.domain.AccessToken;
import com.mall.common.base.CommonResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @Author DongJunTao
 * @Description 登录成功后的逻辑处理（返回token信息）
 * @Date 2021/5/12 15:08
 * @Version 1.0
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    ClientDetailsService clientDetailsService = null;

    AuthorizationServerTokenServices authorizationServerTokenServices = null;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        long start = System.currentTimeMillis();
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(httpServletRequest.getServletContext());
        clientDetailsService = (ClientDetailsService) applicationContext.getBean("clientDetailsService");
        authorizationServerTokenServices = (AuthorizationServerTokenServices) applicationContext.getBean("defaultAuthorizationServerTokenServices");

        String clientId = httpServletRequest.getParameter("clientId");
        //获取 ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        //密码授权 模式, 组建 authentication
        TokenRequest tokenRequest = new TokenRequest(Collections.EMPTY_MAP,clientId,clientDetails.getScope(),"password");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        //构造accessToken
        AccessToken tokenInfo = null;
        if (accessToken != null) {
            tokenInfo = new AccessToken(accessToken.getValue(),accessToken.getTokenType(),accessToken.getRefreshToken().getValue(),
                    accessToken.getExpiresIn(),accessToken.getScope(),accessToken.getAdditionalInformation());
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(CommonResult.success(tokenInfo)));
        System.out.println("onAuthenticationSuccess = "+(System.currentTimeMillis()-start));
    }

}
