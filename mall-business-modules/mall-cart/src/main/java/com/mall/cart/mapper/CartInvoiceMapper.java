package com.mall.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.cart.entity.CartInvoiceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author DongJunTao
 * @Description 购物车-发票信息
 * @Date 2022/5/29 21:46
 * @Version 1.0
 */
@Mapper
public interface CartInvoiceMapper extends BaseMapper<CartInvoiceEntity> {
}
