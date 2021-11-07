package com.mall.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.coupon.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 优惠券mapper
 * @Date 2021/10/31 22:02
 * @Version 1.0
 */
@Mapper
public interface CouponMapper extends BaseMapper<CouponEntity> {
}
