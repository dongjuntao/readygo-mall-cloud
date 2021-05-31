package com.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.entity.AdminUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 后台管理用户Mapper
 * @Date 2021/5/28 9:29
 * @Version 1.0
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserEntity> {
    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    AdminUserEntity getAdminUserById(long id);

    /**
     * 根据用户名查看用户列表
     * @param userName
     * @return
     */
    AdminUserEntity getAdminUserByUserName(String userName);

    /**
     * 根据用户id查询所有的菜单
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);
}
