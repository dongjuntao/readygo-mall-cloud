package com.mall.auth.config;

import com.mall.base.constant.OAuth2Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/30 10:38
 * @Version 1.0
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     *  配置资源接口安全，http.authorizeRequests()针对的所有url，但是由于登录页面url包含在其中，这么配置会进行token校验，校验不通过返回错误json，
     *  而授权码模式获取code时需要重定向登录页面，重定向过程并不能携带token，所有不能用http.authorizeRequests()，
     *  而是用requestMatchers().antMatchers("")，这里配置的是需要资源接口拦截的url数组
     * @param http
     * @return void
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    //配置需要保护的资源接口
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .requestMatchers()
                .antMatchers("/user","/test/need_token","/update","/logout","/test/need_admin","/test/scope","/oauth/*")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and().httpBasic();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(OAuth2Constant.RESOURCE_ID) // 配置资源id，这里的资源id和授权服务器中的资源id一致
                .stateless(true); //设置这些资源仅基于令牌认证
    }
}
