package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.admin.mapper.AdminUserMapper;
import com.mall.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/4/28 15:54
 * @Version 1.0
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends
        ServiceImpl<AdminUserMapper,AdminUserEntity> implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    @Override
    public AdminUserEntity getAdminUserById(long id) {
        AdminUserEntity adminUser = adminUserMapper.getAdminUserById(id);
        return adminUser;
    }

    /**
     * 根据用户名查看用户列表
     * @param userName
     * @return
     */
    @Override
    public AdminUserEntity getAdminUserByUserName(String userName) {

        return adminUserMapper.getAdminUserByUserName(userName);
    }

    /**
     * 根据用户id查询所有的菜单
     * @param userId
     * @return
     */
    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return adminUserMapper.queryAllMenuId(userId);
    }


}
