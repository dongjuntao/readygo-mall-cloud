package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.AdminUserEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 管理用户service
 * @Date 2021/4/28 15:52
 * @Version 1.0
 */
public interface AdminUserService extends IService<AdminUserEntity> {

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    AdminUserEntity getAdminUserById(long id);

    /**
     * 根据用户名查看用户
     * @param userName
     * @return
     */
    AdminUserEntity getAdminUserByUserName(String userName);

    List<Long> queryAllMenuId(Long userId);
}
