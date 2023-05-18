package com.mall.common.base.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/5/14 15:34
 * @Version 1.0
 */
@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(feignHttpMessageConverter());
    }

    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new GateWayMappingJackson2HttpMessageConverter());
        return () -> httpMessageConverters;
    }

    public class GateWayMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        GateWayMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE +";charset=UTF-8"));
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE +";charset=UTF-8"));
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_PLAIN_VALUE +";charset=UTF-8"));
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_XML_VALUE +";charset=UTF-8"));
            mediaTypes.add(MediaType.valueOf("text/json;charset=utf-8"));
            mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8"));
            setSupportedMediaTypes(mediaTypes);
        }
    }
}