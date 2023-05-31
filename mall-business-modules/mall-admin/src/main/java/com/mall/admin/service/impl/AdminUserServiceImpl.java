package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.admin.mapper.AdminUserMapper;
import com.mall.admin.service.AdminUserService;
import com.mall.admin.service.UserRoleService;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
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
     * 根据用户名和用户类型查看用户
     * @param userName 用户名
     * @param userType 用户类型  0:【平台管理员】（该类管理员属于平台所有，管理平台相关事宜） 1:【店铺管理员】包括【入驻商户】【自营商户】
     * @param id 用户id
     * @return
     */
    @Override
    public AdminUserEntity getUserByParams(String userName, Integer userType, Long id) {
        return baseMapper.selectOne(
                new QueryWrapper<AdminUserEntity>()
                        .eq(StringUtils.isNotBlank(userName), "user_name", userName)
                        .eq(userType != null, "user_type",userType)
                        .ne(id != null, "id",id));//排除id

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
     * @param userName 用户名
     * @param userType 用户类型  0:【平台管理员】（该类管理员属于平台所有，管理平台相关事宜） 1:【店铺管理员】包括【入驻商户】【自营商户】
     * @param merchantType 商户（店铺）类型 0:入驻商户 1:自营商户
     * @param authStatus 审核状态
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    @Override
    public PageUtil queryPage(String userName, Integer userType, Integer merchantType,
                              Integer authStatus, Integer pageNum, Integer pageSize) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        IPage<AdminUserEntity> page = this.page(
                new PageBuilder<AdminUserEntity>().getPage(pageParams),
                new QueryWrapper<AdminUserEntity>()
                        .like(StringUtils.isNotBlank(userName), "user_name", userName)
                        .eq(userType != null, "user_type", userType)
                        .eq(merchantType != null, "merchant_type", merchantType)
                        .eq(authStatus != null, "auth_status", authStatus)
        );
        return new PageUtil(page);
    }

    /**
     * 根据条件查询所有管理员
     * @param userType 用户类型
     * @param authStatus 审核状态
     * @param merchantType 商户（店铺）类型 0:入驻商户 1:自营商户
     * @return
     */
    @Override
    public List<AdminUserEntity> queryByParams(Integer userType, Integer authStatus, Integer merchantType) {
        List<AdminUserEntity> adminUserEntityList = this.list(new QueryWrapper<AdminUserEntity>()
                .eq(userType != null, "user_type", userType)
                .eq(merchantType != null, "merchant_type", merchantType)
                .eq(authStatus != null, "auth_status", authStatus));
        return adminUserEntityList;
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
    public int saveAdmin(AdminUserEntity adminUserEntity) {
        //判断用户名是否重复
        AdminUserEntity oldUser = this.getUserByParams(adminUserEntity.getUserName(), null, null);
        if (oldUser != null){
            return -1;
        }
        //平台管理员
        if (adminUserEntity.getUserType() == 0) {
            adminUserEntity.setAuthStatus(1);
        }else {
            //自营商户,自动审核通过
            if (adminUserEntity.getMerchantType() == 1) {
                adminUserEntity.setAuthStatus(1);
            } else {
                adminUserEntity.setAuthStatus(0);
            }
        }
        adminUserEntity.setStatus(adminUserEntity.getStatus() == null ? 1 : adminUserEntity.getStatus());
        adminUserEntity.setCreateTime(new Date());
        adminUserEntity.setPassword(new BCryptPasswordEncoder().encode(adminUserEntity.getPassword()));
        this.save(adminUserEntity);
        //保存用户与角色关系
        userRoleService.saveOrUpdate(adminUserEntity.getId(), adminUserEntity.getRoleIdList());
        return 1;
    }

    /**
     * 修改用户
     * @param adminUserEntity
     */
    @Override
    @Transactional
    public int update(AdminUserEntity adminUserEntity) {
        AdminUserEntity oldUser = this.getUserByParams(adminUserEntity.getUserName(),
                null, adminUserEntity.getId());
        if (oldUser != null){
            return -1;
        }
        //密码不为空，修改，为空，保持之前的密码不变
        if(StringUtils.isNotBlank(adminUserEntity.getPassword())){
            adminUserEntity.setPassword(new BCryptPasswordEncoder().encode(adminUserEntity.getPassword()));
        }else {
            adminUserEntity.setPassword(this.getAdminUserById(adminUserEntity.getId()).getPassword());
        }
        this.updateById(adminUserEntity);
        //保存用户与角色关系
        userRoleService.saveOrUpdate(adminUserEntity.getId(), adminUserEntity.getRoleIdList());
        return 1;
    }

    /**
     * 商家审核
     * @param id
     * @param adminUserEntity
     * @return
     */
    @Override
    public int audit(Long id, AdminUserEntity adminUserEntity) {
        AdminUserEntity oldEntity = this.getAdminUserById(id);
        if (adminUserEntity == null){
            return -1;
        }
        oldEntity.setAuthStatus(adminUserEntity.getAuthStatus());
        oldEntity.setAuthOpinion(adminUserEntity.getAuthOpinion());
        oldEntity.setRoleIdList(adminUserEntity.getRoleIdList());
        this.updateById(oldEntity);
        //保存用户与角色关系
        userRoleService.saveOrUpdate(oldEntity.getId(), oldEntity.getRoleIdList());
        return 1;
    }

    /**
     * 根据id集合，查询所有商户信息
     * @param ids
     * @return
     */
    @Override
    public List<AdminUserEntity> getByIds(Long[] ids) {
        return baseMapper.selectBatchIds(Arrays.asList(ids));
    }
}
