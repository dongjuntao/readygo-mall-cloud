package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.common.redis.util.PageUtil;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户id获取所有的菜单id
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 分页查询所有用户
     * @param params
     * @return
     */
    PageUtil queryPage(Map<String, Object> params);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 保存用户
     */
    void saveAdmin(AdminUserEntity adminUserEntity);

    /**
     * 修改用户
     */
    void update(AdminUserEntity adminUserEntity);
}
