package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.RoleEntity;
import com.mall.admin.mapper.RoleMapper;
import com.mall.admin.service.RoleMenuService;
import com.mall.admin.service.RoleService;
import com.mall.admin.service.UserRoleService;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 角色管理实现类
 * @Date 2021/6/1 11:03
 * @Version 1.0
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;


    /**
     * 分页查询角色列表
     * @param params
     * @return
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String name = String.valueOf(params.get("name"));//角色名称
        Long createUserId = (Long)params.get("createUserId");
        IPage<RoleEntity> page = this.page(
                new PageBuilder<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .eq(createUserId!=null, "create_user_id",createUserId)
        );

        return new PageUtil(page);
    }

    /**
     * 保存角色
     * @param role
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(RoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);
        //保存角色与菜单关系
        roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void update(RoleEntity role) {
        this.updateById(role);
        //保存角色与菜单关系
        roleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    /**
     * 删除角色
     * @param roleIds
     */
    @Override
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));
        //删除角色-菜单关联关系
        roleMenuService.deleteBatch(roleIds);
        //删除用户-角色关联关系
        userRoleService.deleteBatch(roleIds);
    }

    /**
     * 查询角色id列表
     * @param createUserId
     * @return
     */
    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }
}
