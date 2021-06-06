package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.UserRoleEntity;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/3 14:20
 * @Version 1.0
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
