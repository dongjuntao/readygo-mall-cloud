package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.admin.mapper.AdminUserMapper;
import com.mall.admin.service.AdminUserService;
import com.mall.admin.service.UserRoleService;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private UserRoleService userRoleService;

    /**
     * 根据主键id获取用户实体
     * @param id
     * @return
     */
    @Override
    public AdminUserEntity getAdminUserById(long id) {
        return this.getById(id);
    }

    /**
     * 根据用户名查看用户
     * @param userName
     * @return
     */
    @Override
    public AdminUserEntity getAdminUserByUserName(String userName) {
        return  baseMapper.selectOne(
                new QueryWrapper<AdminUserEntity>()
                        .eq(StringUtils.isNotBlank(userName), "user_name", userName));

    }

    /**
     * 根据用户id查询所有的菜单
     * @param userId
     * @return
     */
    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    /**
     * 分页查询所有用户
     * @param params
     * @return
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String userName = String.valueOf(params.get("userName"));//用户名
        IPage<AdminUserEntity> page = this.page(
                new PageBuilder<AdminUserEntity>().getPage(params),
                new QueryWrapper<AdminUserEntity>()
                        .like(StringUtils.isNotBlank(userName), "user_name", userName)
        );

        return new PageUtil(page);
    }

    /**
     * 删除用户
     * @param userIds
     */
    @Override
    public void deleteBatch(Long[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
        //删除用户与角色关系
        for (Long userId : userIds) {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", userId);
            userRoleService.removeByMap(map);
        }
    }

    /**
     * 保存用户
     * @param adminUserEntity
     */
    @Override
    @Transactional
    public void saveAdmin(AdminUserEntity adminUserEntity) {
        adminUserEntity.setCreateTime(new Date());
        adminUserEntity.setPassword(new BCryptPasswordEncoder().encode(adminUserEntity.getPassword()));
        this.save(adminUserEntity);
        //保存用户与角色关系
        userRoleService.saveOrUpdate(adminUserEntity.getId(), adminUserEntity.getRoleIdList());
    }

    /**
     * 修改用户
     * @param adminUserEntity
     */
    @Override
    @Transactional
    public void update(AdminUserEntity adminUserEntity) {
        //密码不为空，修改，为空，保持之前的密码不变
        if(StringUtils.isNotBlank(adminUserEntity.getPassword())){
            adminUserEntity.setPassword(new BCryptPasswordEncoder().encode(adminUserEntity.getPassword()));
        }else {
            adminUserEntity.setPassword(this.getAdminUserById(adminUserEntity.getId()).getPassword());
        }
        this.updateById(adminUserEntity);
        //保存用户与角色关系
        userRoleService.saveOrUpdate(adminUserEntity.getId(), adminUserEntity.getRoleIdList());
    }
}
