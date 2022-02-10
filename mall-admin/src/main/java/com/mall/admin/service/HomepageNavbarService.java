package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.HomepageNavbarEntity;
import com.mall.common.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 首页导航栏Service
 * @Date 2022/2/10 22:49
 * @Version 1.0
 */
public interface HomepageNavbarService extends IService<HomepageNavbarEntity> {

    /**
     * 分页查询导航栏列表
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 根据条件查询所有导航栏
     * @param params
     * @return
     */
    List<HomepageNavbarEntity> getByParams(Map<String, Object> params);

    /**
     * 保存导航
     * @param homepageNavbarEntity
     * @return
     */
    int saveHomepageNavbar(HomepageNavbarEntity homepageNavbarEntity);

    /**
     * 根据参数查询导航实体
     * @param params
     * @return
     */
    HomepageNavbarEntity getHomepageNavbarByParams(Map<String, Object> params);

    /**
     * 修改导航
     * @param homepageNavbarEntity
     * @return
     */
    int updateHomepageNavbar(HomepageNavbarEntity homepageNavbarEntity);

    /**
     * 根据主键id获取导航实体
     * @param id
     * @return
     */
    HomepageNavbarEntity getHomepageNavbarById(long id);

    /**
     * 删除导航（支持批量）
     * @param navbarIds
     */
    void deleteBatch(Long[] navbarIds);
}
