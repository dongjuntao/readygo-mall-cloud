package com.mall.auth.impl;

import com.mall.admin.api.feign.AdminUserService;
import com.mall.auth.domain.SecurityUser;
import com.mall.base.constant.MessageConstant;
import com.mall.base.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        UserDTO userDTO = adminUserService.getAdminUserByUserName(userName);
        if (userDTO== null) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        List list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ADMIN"));

        SecurityUser securityUser = new SecurityUser(userDTO.getUserName(),userDTO.getPassword(),
                true,
                true,
                true,
                true, list);
        securityUser.setAge(userDTO.getAge());
        securityUser.setEmail(userDTO.getEmail());
        securityUser.setMobile(userDTO.getMobile());
        return securityUser;
    }
}
