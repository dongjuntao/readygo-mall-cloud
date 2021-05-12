package com.mall.auth.impl;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/30 13:52
 * @Version 1.0
 */
@Component
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }
}
