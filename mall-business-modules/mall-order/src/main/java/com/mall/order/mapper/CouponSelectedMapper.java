package com.mall.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.order.entity.CouponSelectedEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 购物车中已选中的优惠券Mapper
 * @Date 2022/6/5 15:26
 * @Version 1.0
 */
@Mapper
public interface CouponSelectedMapper extends BaseMapper<CouponSelectedEntity> {
}
