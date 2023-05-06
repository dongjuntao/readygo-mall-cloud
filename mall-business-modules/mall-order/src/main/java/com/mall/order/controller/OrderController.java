package com.mall.order.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
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
     * 查询订单列表
     * @param params 查询参数
     * @return
     */
    @GetMapping("getOrderList")
    public CommonResult getOrderList(@RequestParam Map<String,Object> params) {
        params.put("memberId", CurrentUserContextUtil.getCurrentUserInfo().getUserId());
        PageUtil page = orderService.queryPage(params);
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
        if ("TRADE".equals(orderType)) {
            orderSkuCountList = orderDetailService.getSkuIdAndCountByTradeCode(code);
        } else if ("ORDER".equals(orderType)) {
            orderSkuCountList = orderDetailService.getSkuIdAndCountByOrderCode(code);
        }
        return CommonResult.success(orderSkuCountList);
    }

    /**
     * 根据参数获取订单信息
     * @param params 参数
     * @return
     */
    @GetMapping("getOrderByParams")
    public CommonResult getTradeByParams(@RequestParam Map<String, Object> params) {
        OrderEntity order = orderService.getOrderByParams(params);
        return CommonResult.success(order);
    }

    /**
     * 根据参数获取订单信息（包括订单明细）
     * @param params 参数
     * @return
     */
    @GetMapping("getOrderAndDetailByParams")
    public CommonResult getOrderAndDetailByParams(@RequestParam Map<String, Object> params) {
        OrderEntity order = orderService.getOrderAndDetailByParams(params);
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
}
