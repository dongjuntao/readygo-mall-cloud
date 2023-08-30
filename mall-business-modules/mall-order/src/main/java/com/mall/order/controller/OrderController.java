package com.mall.order.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.order.constant.OrderTypeConstant;
import com.mall.order.entity.OrderDetailEntity;
import com.mall.order.entity.OrderEntity;
import com.mall.order.service.OrderDetailService;
import com.mall.order.service.OrderService;
import com.mall.order.vo.OrderSkuCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 订单controller
 * @Date 2022/6/30 21:28
 * @Version 1.0
 */
@RestController
@RequestMapping( "order")
public class OrderController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询订单
     * @param pageNum
     * @param pageSize
     * @param code 订单号
     * @param status 订单状态
     * @return
     */
    @GetMapping("getOrderList")
    public CommonResult getOrderList(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                     @RequestParam(value = "code",required = false) String code,
                                     @RequestParam(value = "status",required = false) String status) {
        Long memberId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        PageUtil page = orderService.queryPage(pageNum,pageSize,memberId,null,code,status);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 修改状态
     * @param code
     * @return
     */
    @PostMapping("updateOrderStatus")
    public CommonResult updateOrderStatus(@RequestParam("code") String code,
                                          @RequestParam("orderStatus") String orderStatus) {
        orderService.updateOrderStatus(code, orderStatus);
        return CommonResult.success();
    }

    /**
     * 根据交易号所有子订单skuId和数量
     * @param code 交易号
     * @return
     */
    @GetMapping("getSkuIdAndCount")
    public CommonResult getSkuIdAndCount(@RequestParam("code") String code,
                                         @RequestParam("orderType") String orderType) {
        List<OrderSkuCountVO> orderSkuCountList = new ArrayList<>();
        if (OrderTypeConstant.TRADE.equals(orderType)) {
            orderSkuCountList = orderDetailService.getSkuIdAndCountByTradeCode(code);
        } else if (OrderTypeConstant.ORDER.equals(orderType)) {
            orderSkuCountList = orderDetailService.getSkuIdAndCountByOrderCode(code);
        }
        return CommonResult.success(orderSkuCountList);
    }

    /**
     * 根据参数获取订单信息
     * @return
     */
    @GetMapping("getOrderByParams")
    public CommonResult getTradeByParams(@RequestParam(value = "code",required = false) String code) {
        OrderEntity order = orderService.getOrderByParams(code);
        return CommonResult.success(order);
    }

    /**
     * 根据参数获取订单信息（包括订单明细）
     * @param code 参数
     * @return
     */
    @GetMapping("getOrderAndDetailByParams")
    public CommonResult getOrderAndDetailByParams(@RequestParam(value = "code") String code) {
        OrderEntity order = orderService.getOrderAndDetailByParams(code);
        return CommonResult.success(order);
    }


    /**
     * 取消订单
     * @param code
     * @return
     */
    @PutMapping("cancelOrder")
    public CommonResult cancelOrder(@RequestParam("code") String code,
                                    @RequestParam("cancelReason") String cancelReason) {
        orderService.cancelOrder(code, cancelReason);
        return CommonResult.success();
    }

    /**
     * 删除订单
     * @param code
     * @return
     */
    @DeleteMapping("deleteOrder")
    public CommonResult deleteOrder(@RequestParam("code") String code) {
        orderService.deleteOrder(code);
        return CommonResult.success();
    }

    /**
     * 根据子订单subCode获取子订单信息
     * @param subCode
     * @return
     */
    @GetMapping("getOrderDetailByParams")
    public CommonResult getOrderDetailByParams(@RequestParam(value = "subCode") String subCode) {
        OrderDetailEntity orderDetail = orderDetailService.getOrderDetailByParams(subCode);
        return CommonResult.success(orderDetail);
    }

    /**
     *  确认收货，父子订单也都确认收货
     * @param code 订单编号，确认收货
     */
    @PutMapping("confirmReceiptAll")
    public CommonResult confirmReceiptAll(@RequestParam("code") String code) {
        orderService.confirmReceiptAll(code);
        return CommonResult.success();
    }

    /**
     * 确认收货（子订单）
     * @param subCode 子订单编号
     */
    @PutMapping("confirmReceipt")
    public CommonResult confirmReceipt(@RequestParam("subCode") String subCode) {
        orderService.confirmReceipt(subCode);
        return CommonResult.success();
    }
}
