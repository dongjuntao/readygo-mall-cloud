package com.mall.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.admin.entity.ShippingInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/8/31 22:10
 * @Version 1.0
 */
@Mapper
public interface ShippingInfoMapper extends BaseMapper<ShippingInfoEntity> {

    IPage<ShippingInfoEntity> queryPage(
            @Param("page") Page<ShippingInfoEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<ShippingInfoEntity> wrapper);

    ShippingInfoEntity getShippingInfoById(Long id);
}
