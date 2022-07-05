package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.OrderInvoiceEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.enums.OrderStatusEnum;
import com.mall.order.enums.PayStatusEnum;
import com.mall.order.mapper.OrderInvoiceMapper;
import com.mall.order.mapper.OrderMapper;
import com.mall.order.mapper.TradeMapper;
import com.mall.order.service.OrderDetailService;
import com.mall.order.service.OrderService;
import com.mall.order.util.SnowFlakeUtil;
import com.mall.order.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 订单service实现
 * @Date 2022/6/16 19:45
 * @Version 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderInvoiceMapper orderInvoiceMapper;

    @Override
    @Transactional
    public void saveOrder(TradeEntity tradeEntity, TradeDetailVO tradeDetailVO) {
        OrderInvoiceVO orderInvoiceVO = tradeDetailVO.getOrderInvoiceVO(); //发票信息
        RecipientInfoVO recipientInfoVO = tradeDetailVO.getRecipientInfoVO(); //收货人信息
        PayVO payVO = tradeDetailVO.getPayVO(); //商家、商品、及支付相关数据
        List<PayMerchantVO> payMerchantList = payVO.getPayMerchantList(); //各个商家及相关商品信息
        for (int i=0; i<payMerchantList.size(); i++) {
            OrderEntity orderEntity = new OrderEntity();
            PayMerchantVO payMerchantVO = payMerchantList.get(i);
            orderEntity.setCode(SnowFlakeUtil.getSnowFlakeId("O",1,1));
            orderEntity.setTradeId(tradeEntity.getId());
            orderEntity.setMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
            orderEntity.setMemberName(CurrentUserContextUtil.getCurrentUserInfo().getUserName());
            orderEntity.setMerchantId(payMerchantVO.getMerchantId());
            orderEntity.setMerchantName(payMerchantVO.getMerchantName());
            orderEntity.setStatus(OrderStatusEnum.UNPAID);
            orderEntity.setTotalPrice(payMerchantVO.getTotalPrice());
            orderEntity.setPayType(0);
            orderEntity.setRemark(payMerchantVO.getRemark());
            orderEntity.setSource(0);
            orderEntity.setFreight(payMerchantVO.getFreight());
            orderEntity.setCreateTime(new Date());
            //订单基本表数据入库
            orderMapper.insert(orderEntity);
            List<PayGoodsVO> payGoodsList = payMerchantVO.getPayGoodsList();
            List<OrderDetailEntity> orderDetailList = new ArrayList<>();
            payGoodsList.stream().forEach(payGoodsVO -> {
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                orderDetailEntity.setOrderId(orderEntity.getId());
                orderDetailEntity.setSubCode(SnowFlakeUtil.getSnowFlakeId("S",1,1));
                orderDetailEntity.setGoodsCount(payGoodsVO.getCount());
                orderDetailEntity.setGoodsId(payGoodsVO.getGoodsId());
                orderDetailEntity.setGoodsImage(payGoodsVO.getImage());
                orderDetailEntity.setGoodsSellingPrice(payGoodsVO.getSellingPrice());
                orderDetailEntity.setGoodsName(payGoodsVO.getName());
                orderDetailEntity.setGoodsSkuId(payGoodsVO.getGoodsSkuId());
                orderDetailEntity.setGoodsSubTotal(payGoodsVO.getSubTotal());
                orderDetailEntity.setRecipientMobile(recipientInfoVO.getMobile());
                orderDetailEntity.setRecipientDetailAddress(recipientInfoVO.getDetailAddress());
                orderDetailEntity.setRecipientName(recipientInfoVO.getName());
                orderDetailEntity.setRegionNames(recipientInfoVO.getRegionNames());
                orderDetailList.add(orderDetailEntity);
            });
            //订单详细信息入库
            orderDetailService.saveBatch(orderDetailList);
            //保存发票信息
            OrderInvoiceEntity orderInvoiceEntity = new OrderInvoiceEntity();
            BeanUtils.copyProperties(orderInvoiceVO, orderInvoiceEntity);
            orderInvoiceEntity.setOrderId(orderEntity.getId());
            //发票信息入库
            orderInvoiceMapper.insert(orderInvoiceEntity);
        }
    }

    @Override
    @GlobalTransactional
    @Transactional
    public void updateOrderStatus(Long tradeId) {
        QueryWrapper<OrderEntity> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq(tradeId != null, "trade_id", tradeId);
        List<OrderEntity> orderList = orderMapper.selectList(orderQueryWrapper);
        List<OrderEntity> newOrderList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (OrderEntity order : orderList) {
                order.setStatus(OrderStatusEnum.PAID);
                newOrderList.add(order);
            }
            this.updateBatchById(newOrderList);
        }
    }
}
