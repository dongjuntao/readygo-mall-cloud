package com.mall.auth.impl;

import com.alibaba.fastjson.JSON;
import com.mall.admin.api.feign.AdminUserService;
import com.mall.auth.domain.SecurityUser;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.MessageConstant;
import com.mall.common.base.dto.AdminUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/30 13:24
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        CommonResult result = adminUserService.getAdminUserByUserName(userName);
        if (result == null || !"200".equals(result.getCode())) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        AdminUserDTO adminUserDTO = JSON.parseObject(JSON.toJSONString(result.getData()), AdminUserDTO.class);
        SecurityUser securityUser = new SecurityUser(
                adminUserDTO.getUserName(),
                adminUserDTO.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(null));
        securityUser.setEmail(adminUserDTO.getEmail());
        securityUser.setMobile(adminUserDTO.getMobile());
        return securityUser;
    }
}
