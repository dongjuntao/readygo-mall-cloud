package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.RoleEntity;
import com.mall.common.redis.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 角色管理接口
 * @Date 2021/6/1 11:02
 * @Version 1.0
 */
public interface RoleService extends IService<RoleEntity> {
    /**
     * 根据条件分页查询角色列表
     * @param params
     * @return
     */
    PageUtil queryPage(Map<String, Object> params);

    /**
     * 保存角色信息
     * @param role
     */
    void saveRole(RoleEntity role);

    /**
     * 修改角色信息
     * @param role
     */
    void update(RoleEntity role);
    /**
     * 批量删除角色
     * @param roleIds
     */
    void deleteBatch(Long[] roleIds);

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

}
