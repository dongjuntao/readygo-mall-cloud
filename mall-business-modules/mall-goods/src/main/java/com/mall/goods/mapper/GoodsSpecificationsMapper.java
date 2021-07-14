package com.mall.goods.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Wrapper;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/25 16:35
 * @Version 1.0
 */
@Mapper
public interface GoodsSpecificationsMapper extends BaseMapper<GoodsSpecificationsEntity> {

    IPage<GoodsSpecificationsEntity> queryPage(
            @Param("page") Page<GoodsSpecificationsEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<GoodsSpecificationsEntity> wrapper);


    GoodsSpecificationsEntity getGoodsSpecificationsAndDetail(@Param("id") Long id);

    List<GoodsSpecificationsEntity> getGoodsSpecificationList();

}
