package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import com.mall.goods.mapper.GoodsSpecificationsMapper;
import com.mall.goods.service.GoodsSpecificationsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

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
    public PageUtil queryPage(Integer pageNum, Integer pageSize, String name, Long adminUserId) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        Page<GoodsSpecificationsEntity> page =
                (Page<GoodsSpecificationsEntity>)new PageBuilder<GoodsSpecificationsEntity>().getPage(pageParams);
        QueryWrapper<GoodsSpecificationsEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        wrapper.eq(adminUserId != null, "admin_user_id", adminUserId);
        IPage<GoodsSpecificationsEntity> iPage = baseMapper.queryPage(page, wrapper);
        return new PageUtil(iPage);
    }

    @Override
    public GoodsSpecificationsEntity getGoodsSpecificationsAndDetail(Long id) {
        return baseMapper.getGoodsSpecificationsAndDetail(id);
    }

    @Override
    public List<GoodsSpecificationsEntity> getGoodsSpecificationList() {
        return baseMapper.getGoodsSpecificationList();
    }
}
