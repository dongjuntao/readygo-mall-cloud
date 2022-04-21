package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.UserRoleEntity;
import com.mall.admin.mapper.UserRoleMapper;
import com.mall.admin.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/3 14:22
 * @Version 1.0
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        this.removeByMap(map);

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }
        //保存用户与角色关系
        for(Long roleId : roleIdList) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            this.save(userRoleEntity);
        }
    }

    /**
     * 查询角色id列表
     * @param userId
     * @return
     */
    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    /**
     * 批量删除
     * @param roleIds
     * @return
     */
    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
