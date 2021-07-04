package com.mall.goods.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/30 16:52
 * @Version 1.0
 */
@Mapper
public interface GoodsMapper extends BaseMapper<GoodsEntity> {

    IPage<GoodsEntity> queryPage(
            @Param("page") Page<GoodsEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<GoodsEntity> wrapper);


    GoodsEntity getGoodsAndSku(Long id);
}
