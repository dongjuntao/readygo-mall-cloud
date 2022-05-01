package com.mall.auth.impl;

import com.alibaba.fastjson.JSON;
import com.mall.admin.api.feign.AdminUserService;
import com.mall.auth.domain.SecurityUser;
import com.mall.common.base.CommonResult;
import com.mall.common.base.constant.MessageConstant;
import com.mall.common.base.dto.CurrentUserInfo;
import com.mall.common.base.utils.MapUtil;
import com.mall.member.api.FeignMemberService;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FeignMemberService feignMemberService;

    @Override
    public UserDetails loadUserByUsername(String userInfo) throws UsernameNotFoundException {
        long start = System.currentTimeMillis();
        String userName = userInfo.split(":")[0];
        //userType
        // 【0:系统管理员，1:入驻商户，2:自营商户，3:商户操作人员】
        // 【0,1,2,3时访问管理员表，4时访问普通会员表】
        Integer userType = Integer.parseInt(userInfo.split(":")[1]);
        CommonResult result = null;
        if (userType == 0 || userType == 1 || userType == 2 || userType == 3) {
            result = adminUserService.getUserByParams(new MapUtil().put("userName",userName).put("userType", userType));
        }else {
            result = feignMemberService.getMemberByParams(new MapUtil().put("userName", userName));
        }
        System.out.println("loadUserByUsername == 差值="+(System.currentTimeMillis()-start));
        if (result == null || !"200".equals(result.getCode())) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        CurrentUserInfo currentUserInfo = JSON.parseObject(JSON.toJSONString(result.getData()), CurrentUserInfo.class);
        SecurityUser securityUser = new SecurityUser(
                currentUserInfo.getUserName(),
                currentUserInfo.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(null));
        securityUser.setId(currentUserInfo.getId());
        securityUser.setUserType(currentUserInfo.getUserType());
        securityUser.setAuthStatus(currentUserInfo.getAuthStatus());
        securityUser.setAvatar(currentUserInfo.getAvatar());
        securityUser.setSex(currentUserInfo.getSex());
        securityUser.setNickName(currentUserInfo.getNickName());
        securityUser.setBirthday(currentUserInfo.getBirthday());
        return securityUser;
    }
}
