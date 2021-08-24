package com.mall.brand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.brand.entity.BrandCategoryEntity;
import com.mall.common.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌分类service
 * @Date 2021/6/16 9:58
 * @Version 1.0
 */
public interface BrandCategoryService extends IService<BrandCategoryEntity> {

    /**
     * 分页按条件查询
     * @param params
     * @return
     */
    PageUtil queryPage(Map<String, Object> params);

    /**
     * 不分页，查询所有
     * @return
     */
    List<BrandCategoryEntity> queryAll();
}
