package com.mall.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.OAuth2Constant;
import com.mall.common.base.dto.CurrentUserInfo;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.gateway.config.IgnoreUrlsConfig;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author DongJunTao
 * @Description token校验（拦截所有接口，进行token鉴权）
 * @Date 2021/6/11 13:43
 * @Version 1.0
 */
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        //白名单放行
        if (ignoreUrlsConfig != null &&
                ignoreUrlsConfig.getUrls().contains(exchange.getRequest().getURI().getPath())) {
            //白名单中，如果有token，则解析token，转发到各个服务, 否则，直接过
            if (!StringUtils.isEmpty(token) && token.startsWith("Bearer")) {
                //解析token, 获取用户信息，传递到各个服务
                try {
                    Claims claims = Jwts.parser().setSigningKey(OAuth2Constant.SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                            .parseClaimsJws(token.substring(7)).getBody();
                    Map<String, Object> currentUserInfoMap = new HashMap<>();
                    currentUserInfoMap.put("userId", String.valueOf(claims.get("userId")));
                    currentUserInfoMap.put("userName", String.valueOf(claims.get("userName")));
                    currentUserInfoMap.put("userType", String.valueOf(claims.get("userType")));
                    Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                        httpHeader.set("currentUserInfo", JSONObject.toJSONString(currentUserInfoMap));
                    };
                    ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
                    exchange.mutate().request(serverHttpRequest).build();
                } catch (Exception e) {
                    try {
                        CommonResult commonResult = CommonResult.fail(ResultCodeEnum.UNAUTHORIZED.getCode(),
                                ResultCodeEnum.UNAUTHORIZED.getMessage());
                        DataBuffer buffer = exchange.getResponse().bufferFactory()
                                .wrap(JSON.toJSONString(commonResult).getBytes(StandardCharsets.UTF_8));
                        ServerHttpResponse response = exchange.getResponse();
                        //指定编码，否则在浏览器中会中文乱码
                        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                        return response.writeWith(Mono.just(buffer));
                    }catch (UnsupportedOperationException ue) {}
                }

            }
            return chain.filter(exchange);
        }
        //token校验
        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer")) {
            try {
                CommonResult commonResult = CommonResult.fail(ResultCodeEnum.UNAUTHORIZED.getCode(),
                        ResultCodeEnum.UNAUTHORIZED.getMessage());
                DataBuffer buffer = exchange.getResponse().bufferFactory()
                        .wrap(JSON.toJSONString(commonResult).getBytes(StandardCharsets.UTF_8));
                ServerHttpResponse response = exchange.getResponse();
                //指定编码，否则在浏览器中会中文乱码
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }catch (UnsupportedOperationException e) {}
        }else {
            try {
                //解析token, 获取用户信息，传递到各个服务
                Claims claims = Jwts.parser().setSigningKey(OAuth2Constant.SIGN_KEY.getBytes(StandardCharsets.UTF_8))
                        .parseClaimsJws(token.substring(7)).getBody();
                Map<String, Object> currentUserInfoMap = new HashMap<>();
                currentUserInfoMap.put("userId", String.valueOf(claims.get("userId")));
                currentUserInfoMap.put("userName", String.valueOf(claims.get("userName")));
                currentUserInfoMap.put("userType", String.valueOf(claims.get("userType")));
                Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                    httpHeader.set("currentUserInfo", JSONObject.toJSONString(currentUserInfoMap));
                };
                ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
                exchange.mutate().request(serverHttpRequest).build();
            }catch (Exception e) {
                try {
                    CommonResult commonResult = CommonResult.fail(ResultCodeEnum.UNAUTHORIZED.getCode(),
                            ResultCodeEnum.UNAUTHORIZED.getMessage());
                    DataBuffer buffer = exchange.getResponse().bufferFactory()
                            .wrap(JSON.toJSONString(commonResult).getBytes(StandardCharsets.UTF_8));
                    ServerHttpResponse response = exchange.getResponse();
                    //指定编码，否则在浏览器中会中文乱码
                    response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                    return response.writeWith(Mono.just(buffer));
                }catch (UnsupportedOperationException ue) {}
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -99;
    }
}
