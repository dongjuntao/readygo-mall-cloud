package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.AdminUserEntity;
import com.mall.common.base.utils.PageUtil;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 根据用户名和用户类型查看用户
     * @param userName 用户名
     * @param userType 用户类型  0:【平台管理员】（该类管理员属于平台所有，管理平台相关事宜） 1:【店铺管理员】包括【入驻商户】【自营商户】
     * @param id 用户id
     * @return
     */
    AdminUserEntity getUserByParams(String userName, Integer userType, Long id);

    /**
     * 根据用户id获取所有的菜单id
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

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
    PageUtil queryPage(String userName, Integer userType, Integer merchantType,
                       Integer authStatus, Integer pageNum, Integer pageSize);

    /**
     * 根据条件查询所有管理员
     * @param params
     * @return
     */
    List<AdminUserEntity> queryByParams(Integer userType, Integer authStatus, Integer merchantType);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 保存用户
     */
    int saveAdmin(AdminUserEntity adminUserEntity);

    /**
     * 修改用户
     */
    int update(AdminUserEntity adminUserEntity);

    /**
     * 商家审核
     * @param id
     * @param adminUserEntity
     * @return
     */
    int audit(Long id, AdminUserEntity adminUserEntity);

    /**
     * 根据id集合，查询所有商户信息
     * @param ids
     * @return
     */
    List<AdminUserEntity> getByIds(Long[] ids);
}
