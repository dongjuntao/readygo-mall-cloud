package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.redis.util.PageBuilder;
import com.mall.common.redis.util.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.mapper.GoodsMapper;
import com.mall.goods.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:54
 * @Version 1.0
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsEntity> implements GoodsService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        Page<GoodsEntity> page = (Page<GoodsEntity>)new PageBuilder<GoodsEntity>().getPage(params);
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        String name = String.valueOf(params.get("name"));//商品名称
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        IPage<GoodsEntity> iPage = baseMapper.queryPage(page, wrapper);
        return new PageUtil(iPage);
    }

    @Override
    public GoodsEntity getGoodsAndSku(Long id) {
        return baseMapper.getGoodsAndSku(id);
    }
}
