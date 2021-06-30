package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.redis.util.PageBuilder;
import com.mall.common.redis.util.PageUtil;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import com.mall.goods.mapper.GoodsSpecificationsMapper;
import com.mall.goods.service.GoodsSpecificationsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/25 16:35
 * @Version 1.0
 */
@Service("goodsSpecificationsService")
public class GoodsSpecificationsServiceImpl extends ServiceImpl<GoodsSpecificationsMapper, GoodsSpecificationsEntity>
        implements GoodsSpecificationsService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        Page<GoodsSpecificationsEntity> page = (Page<GoodsSpecificationsEntity>)new PageBuilder<GoodsSpecificationsEntity>().getPage(params);
        QueryWrapper<GoodsSpecificationsEntity> wrapper = new QueryWrapper<>();
        String name = String.valueOf(params.get("name"));//品牌名称
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        IPage<GoodsSpecificationsEntity> iPage = baseMapper
                .queryPage(page, wrapper);
        return new PageUtil(iPage);
    }

    @Override
    public GoodsSpecificationsEntity getGoodSpecificationsAndDetail(Long id) {
        return this.baseMapper.getGoodSpecificationsAndDetail(id);
    }
}
