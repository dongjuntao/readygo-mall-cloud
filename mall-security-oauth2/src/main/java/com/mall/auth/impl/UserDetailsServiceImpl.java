package com.mall.auth.impl;

import com.alibaba.fastjson.JSON;
import com.mall.admin.api.feign.AdminUserService;
import com.mall.auth.domain.SecurityUser;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.MessageConstant;
import com.mall.common.base.dto.UserDTO;
import com.mall.common.util.MapUtil;
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
    public UserDetails loadUserByUsername(String userInfo) throws UsernameNotFoundException {
        long start = System.currentTimeMillis();
        String userName = userInfo.split(":")[0];
        //userType【0:系统管理员，1:商户管理员，2:普通商城会员】【0和1时访问管理员表，2时访问普通会员表】
        Integer userType = Integer.parseInt(userInfo.split(":")[1]);
        CommonResult result = null;
        if (userType == 0 || userType == 1) {
            result = adminUserService.getUserByParams(new MapUtil().put("userName",userName).put("userType", userType));
        }else {

        }
        System.out.println("loadUserByUsername == 差值="+(System.currentTimeMillis()-start));
        if (result == null || !"200".equals(result.getCode())) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        UserDTO userDTO = JSON.parseObject(JSON.toJSONString(result.getData()), UserDTO.class);
        SecurityUser securityUser = new SecurityUser(
                userDTO.getUserName(),
                userDTO.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(null));
        securityUser.setId(userDTO.getId());
        securityUser.setUserType(userDTO.getUserType());
        securityUser.setAuditStatus(userDTO.getAuditStatus());
        securityUser.setAvatar(userDTO.getAvatar());
        return securityUser;
    }
}
