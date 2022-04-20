package com.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.BrandEntity;
import com.mall.goods.mapper.BrandMapper;
import com.mall.goods.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌 实现类
 * @Date 2021/6/16 10:04
 * @Version 1.0
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String name = String.valueOf(params.get("name"));//品牌名称
        IPage<BrandEntity> page = this.page(
                new PageBuilder<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtil(page);
    }

    @Override
    public BrandEntity queryByParams(Map<String, Object> params) {
        String logo = String.valueOf(params.get("logo"));//品牌名称
        QueryWrapper queryWrapper =
                new QueryWrapper<BrandEntity>().eq(StringUtils.isNotBlank(logo), "logo", logo);
        BrandEntity brandEntity = baseMapper.selectOne(queryWrapper);
        return brandEntity;
    }
}
