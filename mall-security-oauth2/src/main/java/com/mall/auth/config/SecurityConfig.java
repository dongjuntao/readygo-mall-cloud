package com.mall.auth.config;

import com.mall.auth.handler.*;
import com.mall.auth.impl.UserDetailsServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author DongJunTao
 * @Description security配置
 * @Date 2021/4/29 17:30
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

    @Autowired
    private CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Autowired
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    @Autowired
    private CustomizeLogoutHandler customizeLogoutHandler;

    @Autowired
    private CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    /**
     * 配置认证管理器
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 配置密码加密对象（解密时会用到PasswordEncoder的matches判断是否正确）
     * 用户的password和客户端clientSecret用到，所以存的时候存该bean encode过的密码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 这里是对认证管理器的添加配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/static/**");
    }

    /**
     *  安全请求配置,这里配置的是security的部分，这里配置全部通过，安全拦截在资源服务的配置文件中配置，
     *  要不然访问未验证的接口将重定向到登录页面，前后端分离的情况下这样并不友好，无权访问接口返回相关错误信息即可
     * @param http
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        http.requestMatchers()
                .antMatchers("/oauth/**")
                .antMatchers("/auth/**");
        http.authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .and()
                .logout()
                    .logoutUrl("/auth/logout")
                    .addLogoutHandler(customizeLogoutHandler)
                    .logoutSuccessHandler(customizeLogoutSuccessHandler)
                    .invalidateHttpSession(true)
                    .deleteCookies("Admin-TokenKey")
                    .clearAuthentication(true)
                .permitAll()
                .and()
                .formLogin()
                    .usernameParameter("userName")
                    .passwordParameter("password")
                    .loginProcessingUrl("/auth/login")
                    .successHandler(customizeAuthenticationSuccessHandler)
                    .failureHandler(customizeAuthenticationFailureHandler)
                    .permitAll();
        http.exceptionHandling().authenticationEntryPoint(customizeAuthenticationEntryPoint);//未登录时返回值
    }

}
