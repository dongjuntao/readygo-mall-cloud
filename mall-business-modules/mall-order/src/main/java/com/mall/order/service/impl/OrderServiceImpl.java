package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.OrderInvoiceEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.enums.*;
import com.mall.order.mapper.OrderInvoiceMapper;
import com.mall.order.mapper.OrderMapper;
import com.mall.order.service.OrderDetailService;
import com.mall.order.service.OrderService;
import com.mall.order.util.SnowFlakeUtil;
import com.mall.order.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public PageUtil queryPage(Map<String, Object> params) {
        Page<OrderEntity> page = (Page<OrderEntity>)new PageBuilder<OrderEntity>().getPage(params);
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        //订单号
        String code = params.get("code") == null ? null : params.get("code").toString();
        //订单状态
        String status = params.get("status") == null ? null : params.get("status").toString();
        wrapper.like(StringUtils.isNotBlank(code), "oi.code", code)
                .eq(StringUtils.isNotBlank(status), "oi.status", status).orderByDesc("create_time");
        IPage<OrderEntity> iPage = baseMapper.queryPage(page, wrapper);
        return new PageUtil(iPage);
    }

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
            orderEntity.setCode(SnowFlakeUtil.getSnowFlakeId(CodePrefixEnum.O,1,1));
            orderEntity.setTradeId(tradeEntity.getId());
            orderEntity.setTradeCode(tradeEntity.getCode());
            orderEntity.setMemberId(CurrentUserContextUtil.getCurrentUserInfo().getUserId());
            orderEntity.setMemberName(CurrentUserContextUtil.getCurrentUserInfo().getUserName());
            orderEntity.setMerchantId(payMerchantVO.getMerchantId());
            orderEntity.setMerchantName(payMerchantVO.getMerchantName());
            orderEntity.setStatus(OrderStatusEnum.UNPAID);
            orderEntity.setTotalPrice(payMerchantVO.getTotalPrice());
            orderEntity.setFinalPrice(payMerchantVO.getFinalPrice());
            orderEntity.setPayType(PayTypeEnum.ALIPAY);
            orderEntity.setRemark(payMerchantVO.getRemark());
            orderEntity.setSource(0);
            orderEntity.setFreight(payMerchantVO.getFreight());
            orderEntity.setCreateTime(new Date());
            orderEntity.setRecipientMobile(recipientInfoVO.getMobile());
            orderEntity.setRecipientDetailAddress(recipientInfoVO.getDetailAddress());
            orderEntity.setRecipientName(recipientInfoVO.getName());
            orderEntity.setRegionNames(recipientInfoVO.getRegionNames());
            //订单基本表数据入库
            orderMapper.insert(orderEntity);
            List<PayGoodsVO> payGoodsList = payMerchantVO.getPayGoodsList();
            List<OrderDetailEntity> orderDetailList = new ArrayList<>();
            payGoodsList.stream().forEach(payGoodsVO -> {
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                orderDetailEntity.setOrderId(orderEntity.getId());
                orderDetailEntity.setOrderCode(orderEntity.getCode());
                orderDetailEntity.setSubCode(SnowFlakeUtil.getSnowFlakeId(CodePrefixEnum.S,1,1));
                orderDetailEntity.setGoodsCount(payGoodsVO.getCount());
                orderDetailEntity.setGoodsId(payGoodsVO.getGoodsId());
                orderDetailEntity.setGoodsImage(payGoodsVO.getImage());
                orderDetailEntity.setGoodsSellingPrice(payGoodsVO.getSellingPrice());
                orderDetailEntity.setGoodsName(payGoodsVO.getName());
                orderDetailEntity.setGoodsSkuId(payGoodsVO.getGoodsSkuId());
                orderDetailEntity.setGoodsSubTotal(payGoodsVO.getSubTotal());
                orderDetailEntity.setAfterSalesStatus(AfterSalesStatusEnum.NEW);//默认新订单，不能申请售后
                orderDetailEntity.setCommentStatus(CommentStatusEnum.NEW); //默认新订单，不能评价
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
    public void updateOrderStatusByTrade(Long tradeId, String orderStatus) {
        QueryWrapper<OrderEntity> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq(tradeId != null, "trade_id", tradeId);
        List<OrderEntity> orderList = orderMapper.selectList(orderQueryWrapper);
        List<OrderEntity> newOrderList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderList)) {
            for (OrderEntity order : orderList) {
                if ("PAID".equals(orderStatus)) {
                    //修改售后状态为 未申请，表示可以申请售后
                    orderDetailService.updateAfterSalesStatusByOrderId(order.getId(), AfterSalesStatusEnum.NOT_APPLIED);
                }
                order.setStatus(OrderStatusEnum.valueOf(orderStatus));
                newOrderList.add(order);
            }
            this.updateBatchById(newOrderList);
        }
    }

    @Override
    public OrderEntity getOrderByParams(Map<String, Object> params) {
        String code = params.get("code") == null ? null: params.get("code").toString();
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(code), "code", code);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public OrderEntity getOrderAndDetailByParams(Map<String, Object> params) {
        String code = params.get("code") == null ? null: params.get("code").toString();
        OrderEntity orderParams = new OrderEntity();
        orderParams.setCode(code);
        return baseMapper.getOrderAndDetail(orderParams);
    }

    /**
     * 修改订单状态
     * @param code
     * @param orderStatus
     */
    @Override
    public void updateOrderStatus(String code, String orderStatus) {
        QueryWrapper<OrderEntity> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq(StringUtils.isNotBlank(code), "code", code);
        OrderEntity order = orderMapper.selectOne(orderQueryWrapper);
        if (order != null) {
            order.setStatus(OrderStatusEnum.valueOf(orderStatus));
            if ("PAID".equals(orderStatus)) {
                order.setPayTime(new Date());//支付时间
                orderDetailService.updateAfterSalesStatusByOrderId(order.getId(), AfterSalesStatusEnum.NOT_APPLIED);
            }
            orderMapper.updateById(order);
        }
    }

    /**
     * 取消订单
     * @param code
     * @param cancelReason
     */
    @Override
    public void cancelOrder(String code, String cancelReason) {
        QueryWrapper<OrderEntity> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq(StringUtils.isNotBlank(code), "code", code);
        OrderEntity order = orderMapper.selectOne(orderQueryWrapper);
        if (order != null) {
            order.setCancelReason(cancelReason);
            order.setStatus(OrderStatusEnum.CANCELLED);
            orderMapper.updateById(order);
        }
    }

    /**
     * 取消订单
     * @param code
     */
    @Override
    public void deleteOrder(String code) {
        //删除订单表
        QueryWrapper<OrderEntity> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq(StringUtils.isNotBlank(code), "code", code);
        orderMapper.delete(orderQueryWrapper);
        //删除订单明细表数据
        QueryWrapper<OrderDetailEntity> orderDetailEntityQueryWrapper = new QueryWrapper<>();
        orderDetailEntityQueryWrapper.eq(StringUtils.isNotBlank(code), "order_code", code);
    }
}
