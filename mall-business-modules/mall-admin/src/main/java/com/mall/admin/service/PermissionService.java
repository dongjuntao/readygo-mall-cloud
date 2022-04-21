package com.mall.admin.service;

import java.util.Set;

/**
 * @Author DongJunTao
 * @Description 权限service
 * @Date 2021/5/27 14:27
 * @Version 1.0
 */
public interface PermissionService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);
}
