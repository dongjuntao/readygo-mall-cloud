package com.mall.brand.service;

import com.alibaba.druid.sql.PagerUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.brand.entity.BrandEntity;
import com.mall.common.redis.util.PageUtil;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌service
 * @Date 2021/6/16 9:57
 * @Version 1.0
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtil queryPage(Map<String, Object> params);

    BrandEntity queryByParams(Map<String, Object> params);
}
