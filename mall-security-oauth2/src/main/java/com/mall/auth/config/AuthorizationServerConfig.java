package com.mall.auth.config;

import com.mall.auth.domain.SecurityUser;
import com.mall.auth.impl.UserDetailsServiceImpl;
import com.mall.common.base.constant.OAuth2Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author DongJunTao
 * @Description 认证中心配置
 * @Date 2021/4/29 17:20
 * @Version 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier(value = "userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置token存储，这个配置token存到redis中,还有一种常用的是JwkTokenStore
     * jwt的缺点已发布令牌不可控
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }


    /**
     * 配置客户端详情（根据客户的id查询客户端）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(OAuth2Constant.ADMIN_CLIENT_ID)//客户端得ID,比如我们在QQ互联中心申请得。可以写多个。配置 循环
                .secret(passwordEncoder.encode(OAuth2Constant.ADMIN_CLIENT_SECRET)) // 客户端密钥，需要进行加密
                .accessTokenValiditySeconds(7200)//两小时 token 有效时常  0 永久有效
                .refreshTokenValiditySeconds(604800) // refresh_token一周
                .resourceIds(OAuth2Constant.RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")// 支持得授权类型
                .scopes("all", "read", "write");//拥有的 scope  可选
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 采用token转jwt，并添加一些自定义信息（token增强）
        // 默认使用随机UUID生成的token
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter()));
        endpoints
                .tokenEnhancer(tokenEnhancerChain)
                // 配置token存储，一般配置redis存储
                .tokenStore(tokenStore())
                // 配置认证管理器
                .authenticationManager(authenticationManager)
                // 配置用户详情server，密码模式必须
                .userDetailsService(userDetailsService)
                // 配置授权码模式授权码服务,不配置默认为内存模式
                //.authorizationCodeServices(authorizationCodeServices())
                // 配置grant_type模式，如果不配置则默认使用密码模式、简化模式、验证码模式以及刷新token模式，如果配置了只使用配置中，默认配置失效
                // 具体可以查询AuthorizationServerEndpointsConfigurer中的getDefaultTokenGranters方法
                .tokenGranter(tokenGranter(endpoints));
        //  配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        // 是否支持刷新Token
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 设置accessToken和refreshToken的默认超时时间(如果clientDetails的为null就取默认的，如果clientDetails的不为null取clientDetails中的)
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(2));
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
        endpoints.tokenServices(tokenServices);

    }

    /**
     * jwt格式封装token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置jwt加解密秘钥，不设置会随机一个
        jwtAccessTokenConverter.setSigningKey(OAuth2Constant.SIGN_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * token增强,添加一些元信息
     * 用户信息的补充
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(10);
            SecurityUser user = (SecurityUser) authentication.getUserAuthentication().getPrincipal();
            if (user != null) {
                additionalInfo.put("userId", user.getId()); //用户id
                additionalInfo.put("userName", user.getUsername()); //用户名
                additionalInfo.put("userType", user.getUserType()); //用户类型
                additionalInfo.put("authStatus", user.getAuthStatus()); //用户审核状态
                additionalInfo.put("avatar", user.getAvatar()); //用户头像
                additionalInfo.put("sex", user.getSex()); //性别
                additionalInfo.put("nickName", user.getNickName());
                additionalInfo.put("birthday", user.getBirthday());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 创建grant_type列表
     * @param endpoints
     * @return
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> list = new ArrayList<>();
        // 这里配置密码模式、刷新token模式s、授权码模式、简化模式
        list.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        list.add(new RefreshTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        list.add(new AuthorizationCodeTokenGranter(endpoints.getTokenServices(),endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        list.add(new ImplicitTokenGranter(endpoints.getTokenServices(),endpoints.getClientDetailsService(),endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(list);
    }
}
