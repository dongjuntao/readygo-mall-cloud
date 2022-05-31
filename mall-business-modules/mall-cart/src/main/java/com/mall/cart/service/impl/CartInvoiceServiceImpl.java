package com.mall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.cart.entity.CartInvoiceEntity;
import com.mall.cart.mapper.CartInvoiceMapper;
import com.mall.cart.service.CartInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车-发票信息
 * @Date 2022/5/29 21:47
 * @Version 1.0
 */
@Service("cartBillService")
public class CartInvoiceServiceImpl extends ServiceImpl<CartInvoiceMapper, CartInvoiceEntity> implements CartInvoiceService {

    @Autowired
    private CartInvoiceMapper cartInvoiceMapper;

    @Override
    public int saveCartInvoice(CartInvoiceEntity cartBill) {
        this.saveOrUpdate(cartBill);
        return this.saveOrUpdate(cartBill) ? 1 : -1;
    }


    @Override
    public int deleteCartInvoice(Long cartBillId) {
        return baseMapper.deleteById(cartBillId);
    }

    @Override
    public CartInvoiceEntity getCartInvoiceByParams(Map<String, Object> params) {
        Long id = params.get("id") == null ? null: Long.valueOf((params.get("id").toString()));
        Long memberId = params.get("memberId") == null ? null: Long.valueOf((params.get("memberId").toString()));
        QueryWrapper<CartInvoiceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return baseMapper.selectOne(queryWrapper);
    }
}
