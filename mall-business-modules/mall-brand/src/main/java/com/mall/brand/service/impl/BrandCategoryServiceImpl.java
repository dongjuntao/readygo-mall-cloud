package com.mall.brand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.brand.entity.BrandCategoryEntity;
import com.mall.brand.mapper.BrandCategoryMapper;
import com.mall.brand.service.BrandCategoryService;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 品牌分类 实现类
 * @Date 2021/6/16 10:07
 * @Version 1.0
 */
@Service("brandCategoryService")
public class BrandCategoryServiceImpl extends ServiceImpl<BrandCategoryMapper, BrandCategoryEntity>
        implements BrandCategoryService {

    /**
     * 分页按条件查询
     * @param params
     * @return
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String name = String.valueOf(params.get("name"));//品牌名称
        IPage<BrandCategoryEntity> page = this.page(
                new PageBuilder<BrandCategoryEntity>().getPage(params),
                new QueryWrapper<BrandCategoryEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtil(page);
    }

    /**
     * 查询所有（不分页）
     * @return
     */
    @Override
    public List<BrandCategoryEntity> queryAll() {
        return this.list();
    }
}
