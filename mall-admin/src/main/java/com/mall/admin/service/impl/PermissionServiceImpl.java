package com.mall.admin.service.impl;

import com.mall.admin.entity.MenuEntity;
import com.mall.admin.mapper.AdminUserMapper;
import com.mall.admin.mapper.MenuMapper;
import com.mall.admin.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/5/27 14:27
 * @Version 1.0
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == 1){
            List<MenuEntity> menuList = menuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(MenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = adminUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
