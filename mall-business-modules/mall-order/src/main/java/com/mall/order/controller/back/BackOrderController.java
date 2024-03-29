package com.mall.order.controller.back;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.order.entity.OrderEntity;
import com.mall.order.service.OrderDetailService;
import com.mall.order.service.OrderService;
import com.mall.order.vo.OrderShipmentParamsVO;
import com.mall.order.vo.OrderSkuCountVO;
import com.mall.order.vo.RecentSixMonthOrderCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 订单controller（管理端）
 * @Date 2022/6/30 21:28
 * @Version 1.0
 */
@RestController
@RequestMapping( "back/order")
public class BackOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 查询订单列表
     * @return
     */
    @GetMapping("getOrderList")
    public CommonResult getOrderList(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                     @RequestParam(value = "code",required = false) String code,
                                     @RequestParam(value = "status",required = false) String status) {
        Long merchantId = null;
        if (CurrentUserContextUtil.getCurrentUserInfo().getUserType() != 0){
            merchantId = CurrentUserContextUtil.getCurrentUserInfo().getUserId();
        }
        PageUtil page = orderService.queryPage(pageNum,pageSize,null,merchantId,code,status);
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
     * 数量统计
     * @return
     */
    @GetMapping("count")
    public CommonResult count(@RequestParam(value = "startTime", required = false) String startTime,
                              @RequestParam(value = "endTime", required = false) String endTime,
                              @RequestParam(value = "status", required = false) String status,
                              @RequestParam(value = "merchantId", required = false) Long merchantId) {
        int count = orderDetailService.count(startTime, endTime, status, merchantId);
        return CommonResult.success(count);
    }

    /**
     * 销售额统计
     * @return
     */
    @GetMapping("salesVolume")
    public CommonResult salesVolume(@RequestParam(value = "startTime", required = false) String startTime,
                                    @RequestParam(value = "endTime", required = false) String endTime,
                                    @RequestParam(value = "status", required = false) String status,
                                    @RequestParam(value = "merchantId", required = false) Long merchantId) {
        BigDecimal salesVolume = orderDetailService.salesVolume(startTime, endTime, status, merchantId);
        return CommonResult.success(salesVolume);
    }

    /**
     * 近六个月订单量统计
     * @return
     */
    @GetMapping("recentSixMonthOrderCount")
    public CommonResult recentSixMonthOrderCountList(@RequestParam(value = "merchantId", required = false) Long merchantId) {
        List<RecentSixMonthOrderCountVO> recentSixMonthOrderCountList = orderDetailService.getRecentSixMonthOrderCount(merchantId);
        return CommonResult.success(recentSixMonthOrderCountList);
    }

    /**
     * 订单发货
     * @param orderShipmentParams
     * @return
     */
    @PutMapping("shipment")
    public CommonResult shipment(@RequestBody OrderShipmentParamsVO orderShipmentParams) {
        orderService.shipment(orderShipmentParams);
        return CommonResult.success();
    }

}
