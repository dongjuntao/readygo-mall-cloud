package com.mall.admin.service;

import com.mall.admin.entity.HomepageCarouselEntity;
import com.mall.common.base.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商城首页轮播图
 * @Date 2022/3/15 20:11
 * @Version 1.0
 */
public interface HomepageCarouselService {

    /**
     * 分页查询轮播图列表
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 根据条件查询所有轮播图
     * @param params
     * @return
     */
    List<HomepageCarouselEntity> getByParams(Map<String, Object> params);

    /**
     * 保存轮播图
     * @param homepageCarouselEntity
     * @return
     */
    int saveHomepageCarousel(HomepageCarouselEntity homepageCarouselEntity);

    /**
     * 根据参数查询轮播图实体
     * @param params
     * @return
     */
    HomepageCarouselEntity getHomepageCarouselByParams(Map<String, Object> params);

    /**
     * 修改轮播图
     * @param homepageCarouselEntity
     * @return
     */
    int updateHomepageCarousel(HomepageCarouselEntity homepageCarouselEntity);

    /**
     * 根据主键id获取轮播图实体
     * @param id
     * @return
     */
    HomepageCarouselEntity getHomepageCarouselById(long id);

    /**
     * 删除轮播图（支持批量）
     * @param carouselIds
     */
    void deleteBatch(Long[] carouselIds);

    /**
     * 启用 / 禁用
     */
    int enable(Long id, Boolean enable);
}
