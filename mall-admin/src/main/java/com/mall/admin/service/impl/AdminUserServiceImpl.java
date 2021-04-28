package com.mall.admin.service.impl;

import com.mall.admin.service.AdminUserService;
import com.mall.generator.dao.AdminUserMapper;
import com.mall.generator.model.AdminUser;
import com.mall.generator.model.AdminUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/28 15:54
 * @Version 1.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getAdminUserById(long id) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(id);
        return adminUser;
    }

    @Override
    public List<AdminUser> getAdminUserByUserName(String userName) {
        AdminUserExample adminUserExample = new AdminUserExample();
        adminUserExample.createCriteria().andUserNameEqualTo(userName);
        List<AdminUser> adminUserList = adminUserMapper.selectByExample(adminUserExample);
        return adminUserList;
    }
}
