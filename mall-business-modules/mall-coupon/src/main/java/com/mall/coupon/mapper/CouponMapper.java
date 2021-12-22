package com.mall.coupon.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.coupon.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author DongJunTao
 * @Description 优惠券mapper
 * @Date 2021/10/31 22:02
 * @Version 1.0
 */
@Mapper
public interface CouponMapper extends BaseMapper<CouponEntity> {

    /**
     * 分页查询优惠券列表
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CouponEntity> queryPage(
            @Param("page") Page<CouponEntity> page,
            @Param(Constants.WRAPPER) QueryWrapper<CouponEntity> wrapper);
}
