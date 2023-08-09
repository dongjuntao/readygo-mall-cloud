package com.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.entity.OrderEntity;
import com.mall.order.entity.OrderInvoiceEntity;
import com.mall.order.entity.TradeEntity;
import com.mall.order.enums.*;
import com.mall.order.mapper.OrderDetailMapper;
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

import java.util.*;

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
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderInvoiceMapper orderInvoiceMapper;


    /**
     *
     * @param pageNum
     * @param pageSize
     * @param code 订单号
     * @param status 订单状态
     * @return
     */
    @Override
    public PageUtil queryPage(Integer pageNum,Integer pageSize, Long memberId, Long merchantId, String code,String status) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        Page<OrderEntity> page = (Page<OrderEntity>)new PageBuilder<OrderEntity>().getPage(pageParams);
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(code), "oi.code", code)
                .eq(!StringUtils.isEmpty(status), "oi.status", status)
                .eq(memberId != null, "member_id", memberId)
                .eq(merchantId != null, "merchant_id", merchantId)
                .orderByDesc("create_time");
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
                orderDetailEntity.setSubStatus(SubOrderStatusEnum.UNPAID);
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
                if (OrderStatusEnum.UNDELIVERED.name().equals(orderStatus)) {
                    order.setPayTime(new Date());
                }
                orderDetailService.updateSubStatusByOrderId(order.getId(), SubOrderStatusEnum.valueOf(orderStatus));
                order.setStatus(OrderStatusEnum.valueOf(orderStatus));
                newOrderList.add(order);
            }
            this.updateBatchById(newOrderList);
        }
    }

    @Override
    public OrderEntity getOrderByParams(String code) {
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(code), "code", code);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public OrderEntity getOrderAndDetailByParams(String code) {
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
            if (OrderStatusEnum.UNDELIVERED.equals(OrderStatusEnum.valueOf(orderStatus))) {
                order.setPayTime(new Date());//支付时间
            }
            order.setUpdateTime(new Date());
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
        OrderEntity order = getOrderAndDetailByParams(code);
        if (order != null) {
            //父订单修改状态
            order.setCancelReason(cancelReason);
            order.setStatus(OrderStatusEnum.CANCELLED);
            orderMapper.updateById(order);
            //子订单修改状态
            List<OrderDetailEntity> orderDetailList = order.getOrderDetailList();
            if (!CollectionUtils.isEmpty(orderDetailList)) {
                for (OrderDetailEntity orderDetail : orderDetailList) {
                    orderDetail.setSubStatus(SubOrderStatusEnum.CANCELLED);
                }
                orderDetailService.updateBatchById(orderDetailList);
            }
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
        orderDetailMapper.delete(orderDetailEntityQueryWrapper);
    }

    /**
     * 商品发货
     * @param orderShipmentParams
     */
    @Override
    @Transactional
    public void shipment(OrderShipmentParamsVO orderShipmentParams) {
        OrderEntity order = this.getById(orderShipmentParams.getOrderId());
        if (order != null && orderShipmentParams.getIsSplit()) { //拆分订单发货
            //先判断是否全部发货（有可能部分发货）
            if (!CollectionUtils.isEmpty(orderShipmentParams.getSubLogisticsInfoList())) {
                boolean allShipment = true;
                QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_id", order.getId());
                List<OrderDetailEntity> orderDetailList = orderDetailService.list(queryWrapper);
                for (SubLogisticsInfoVO subLogistics: orderShipmentParams.getSubLogisticsInfoList()) {
                    if (StringUtils.isEmpty(subLogistics.getSubLogisticsCompany())
                            || StringUtils.isEmpty(subLogistics.getSubTrackingNumber())) {
                        allShipment = false; //未全部发货
                    }
                    if (!CollectionUtils.isEmpty(orderDetailList)) {
                        for (OrderDetailEntity orderDetail : orderDetailList) {
                            //已经发货的
                            if (SubOrderStatusEnum.DELIVERED.equals(orderDetail.getSubStatus())) {
                                continue;
                            }
                            if (Objects.equals(orderDetail.getId(), subLogistics.getOrderDetailId())) {
                                if (!StringUtils.isEmpty(subLogistics.getSubLogisticsCompany())
                                        && !StringUtils.isEmpty(subLogistics.getSubTrackingNumber())) {
                                    orderDetail.setSubLogisticsCompany(subLogistics.getSubLogisticsCompany());
                                    orderDetail.setSubTrackingNumber(subLogistics.getSubTrackingNumber());
                                    orderDetail.setSubStatus(SubOrderStatusEnum.DELIVERED);
                                }
                            }
                        }
                        orderDetailService.updateBatchById(orderDetailList); //批量更新子订单信息
                    }
                }
                if (allShipment) {
                    order.setStatus(OrderStatusEnum.DELIVERED); //已发货
                } else {
                    order.setStatus(OrderStatusEnum.PARTIAL_DELIVERED); //部分发货
                }
                order.setUpdateTime(new Date());
                order.setIsSplit(orderShipmentParams.getIsSplit());
                this.updateById(order); //更新父订单信息
            }
        } else if (order != null && !orderShipmentParams.getIsSplit()) { //一起发货
            if (order != null) {
                order.setLogisticsCompany(orderShipmentParams.getLogisticsCompany());
                order.setTrackingNumber(orderShipmentParams.getTrackingNumber());
                order.setStatus(OrderStatusEnum.DELIVERED);//已发货
                order.setUpdateTime(new Date());
                order.setIsSplit(orderShipmentParams.getIsSplit());
                this.updateById(order); //更新父订单信息
                QueryWrapper<OrderDetailEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_id", order.getId());
                List<OrderDetailEntity> orderDetailList = orderDetailService.list(queryWrapper);
                if (!CollectionUtils.isEmpty(orderDetailList)) {
                    for (OrderDetailEntity orderDetail : orderDetailList) {
                        orderDetail.setSubLogisticsCompany(orderShipmentParams.getLogisticsCompany());
                        orderDetail.setSubTrackingNumber(orderShipmentParams.getTrackingNumber());
                        orderDetail.setSubStatus(SubOrderStatusEnum.DELIVERED); //已返货
                    }
                    orderDetailService.updateBatchById(orderDetailList); //批量更新子订单信息
                }
            }
        }
    }

    /**
     * 确认收货，父子订单也都确认收货
     * @param code 订单编号，确认收货
     */
    @Override
    @Transactional
    public void confirmReceiptAll(String code) {
        OrderEntity orderParams = new OrderEntity();
        orderParams.setCode(code);
        OrderEntity order = orderMapper.getOrderAndDetail(orderParams);
        if (order != null) {
            //修改父订单状态
            order.setStatus(OrderStatusEnum.FINISHED);
            orderMapper.updateById(order);
            //修改子订单状态
            List<OrderDetailEntity> orderDetailList = order.getOrderDetailList();
            if (!CollectionUtils.isEmpty(orderDetailList)) {
                for (OrderDetailEntity orderDetail : orderDetailList) {
                    orderDetail.setSubStatus(SubOrderStatusEnum.FINISHED);
                    orderDetail.setCommentStatus(CommentStatusEnum.NOT_COMMENTED);
                    orderDetail.setAfterSalesStatus(AfterSalesStatusEnum.NOT_APPLIED);
                }
                orderDetailService.updateBatchById(orderDetailList);
            }
        }
    }

    /**
     * 确认收货（子订单）
     * @param subCode 子订单编号
     */
    @Override
    public void confirmReceipt(String subCode) {
        QueryWrapper<OrderDetailEntity> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq(!StringUtils.isEmpty(subCode), "sub_code", subCode);
        OrderDetailEntity orderDetail = orderDetailMapper.selectOne(orderDetailQueryWrapper);
        if (orderDetail != null) {
            orderDetail.setSubStatus(SubOrderStatusEnum.FINISHED);
            orderDetail.setCommentStatus(CommentStatusEnum.NOT_COMMENTED);
            orderDetail.setAfterSalesStatus(AfterSalesStatusEnum.NOT_APPLIED);
            orderDetailMapper.updateById(orderDetail);
            //处理该子订单的父订单状态（父订单下所有子订单都收货时，父订单改为已收货）
            QueryWrapper<OrderDetailEntity> orderDetailListQueryWrapper = new QueryWrapper<>();
            orderDetailListQueryWrapper.eq("order_id", orderDetail.getOrderId());
            List<OrderDetailEntity> orderDetailEntityList = orderDetailService.list(orderDetailListQueryWrapper);
            long count = orderDetailEntityList.stream().filter(od -> od.getSubStatus().equals(SubOrderStatusEnum.DELIVERED)).count();
            if (count == 0) {
                OrderEntity orderEntity = getById(orderDetail.getOrderId());
                orderEntity.setStatus(OrderStatusEnum.FINISHED);
                this.updateById(orderEntity);
            }

        }
    }
}
