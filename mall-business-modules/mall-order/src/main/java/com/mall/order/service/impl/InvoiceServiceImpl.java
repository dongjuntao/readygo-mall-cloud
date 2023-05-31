package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.order.entity.InvoiceEntity;
import com.mall.order.mapper.InvoiceMapper;
import com.mall.order.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 购物车-发票信息
 * @Date 2022/5/29 21:47
 * @Version 1.0
 */
@Service("invoiceService")
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, InvoiceEntity> implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public int saveInvoice(InvoiceEntity cartBill) {
        this.saveOrUpdate(cartBill);
        return this.saveOrUpdate(cartBill) ? 1 : -1;
    }


    @Override
    public int deleteInvoice(Long cartBillId) {
        return baseMapper.deleteById(cartBillId);
    }

    @Override
    public InvoiceEntity getInvoiceByParams(Long id, Long memberId) {
        QueryWrapper<InvoiceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(memberId != null, "member_id", memberId);
        return baseMapper.selectOne(queryWrapper);
    }
}
