package com.mall.auth.service;

//import com.mall.generator.dao.AdminUserMapper;
//import com.mall.generator.model.AdminUser;
//import com.mall.generator.model.AdminUserExample;
//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/30 13:24
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private AdminUserMapper adminUserMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AdminUserExample example = new AdminUserExample();
//        example.createCriteria().andUserNameEqualTo(username);
//        List<AdminUser> adminUserList = adminUserMapper.selectByExample(example);
        return null;
    }
}
