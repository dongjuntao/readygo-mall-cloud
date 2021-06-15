package com.mall.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.gateway.config.IgnoreUrlsConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

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
        //白名单放行
        if (ignoreUrlsConfig != null &&
                ignoreUrlsConfig.getUrls().contains(exchange.getRequest().getURI().getPath())) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        System.out.println("token === " + token);
        if (StringUtils.isEmpty(token)) {
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
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -99;
    }
}
