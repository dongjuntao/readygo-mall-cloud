package com.mall.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 角色管理Mapper
 * @Date 2021/6/1 10:47
 * @Version 1.0
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity> {
    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

}
