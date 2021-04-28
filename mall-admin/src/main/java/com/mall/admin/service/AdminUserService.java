package com.mall.admin.service;

import com.mall.generator.model.AdminUser;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 管理用户service
 * @Date 2021/4/28 15:52
 * @Version 1.0
 */
public interface AdminUserService {

    AdminUser getAdminUserById(long id);

    List<AdminUser> getAdminUserByUserName(String userName);
}
