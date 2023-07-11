package com.mall.admin.controller;

import cn.hutool.core.date.DateUtil;
import com.mall.admin.enums.GoodsStatusEnum;
import com.mall.admin.enums.OrderStatusEnum;
import com.mall.common.base.CommonResult;
import com.mall.common.base.dto.CurrentUserInfo;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.goods.api.FeignGoodsService;
import com.mall.member.api.FeignMemberService;
import com.mall.order.api.feign.FeignBackOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 管理端首页 统计
 * @Date 2023/7/7 20:19
 * @Version 1.0
 */
@RestController
@RequestMapping("homepage/data")
public class HomepageStatisticsController {

    @Autowired
    private FeignBackOrderService feignBackOrderService;

    @Autowired
    private FeignGoodsService feignGoodsService;

    @Autowired
    private FeignMemberService feignMemberService;


    @GetMapping("statistics")
    public CommonResult statistics() {
        CurrentUserInfo currentUserInfo = CurrentUserContextUtil.getCurrentUserInfo();
        Map<String, Object> resultMap = new HashMap<>(); //统计结果
        //总销售额
        CommonResult salesVolumeResult;
        if (currentUserInfo.getUserId() == 1) {
            salesVolumeResult = feignBackOrderService.salesVolume(null, null,  OrderStatusEnum.FINISHED.toString(), null);
        } else {
            salesVolumeResult = feignBackOrderService.salesVolume(null, null,  OrderStatusEnum.FINISHED.toString(), currentUserInfo.getUserId());
        }
        resultMap.put("salesVolume", salesVolumeResult.getData());

        //商品总数
        CommonResult goodsCountResult;
        if (currentUserInfo.getUserId() == 1) {
            goodsCountResult = feignGoodsService.count(GoodsStatusEnum.ON_SALE.toString(), null);
        } else {
            goodsCountResult = feignGoodsService.count(GoodsStatusEnum.ON_SALE.toString(), currentUserInfo.getUserId());
        }
        resultMap.put("totalGoodsCount", goodsCountResult.getData());

        //订单总数
        CommonResult totalResult;
        if (currentUserInfo.getUserId() == 1) {
            totalResult = feignBackOrderService.count(null, null, OrderStatusEnum.FINISHED.toString(),null);
        } else {
            totalResult = feignBackOrderService.count(null, null, OrderStatusEnum.FINISHED.toString(),currentUserInfo.getUserId());
        }
        resultMap.put("totalOrderCount", totalResult.getData());

        //本月订单数
        String firstDayOfMonth = DateUtil.format(DateUtil.beginOfMonth(new Date()),com.mall.common.base.utils.DateUtil.YYYY_DD_MM);
        String today = DateUtil.format(new Date(),com.mall.common.base.utils.DateUtil.YYYY_DD_MM);
        CommonResult thisMonthResult;
        if (currentUserInfo.getUserId() == 1) {
            thisMonthResult = feignBackOrderService.count(firstDayOfMonth, today, OrderStatusEnum.FINISHED.toString(), null);
        } else {
            thisMonthResult = feignBackOrderService.count(firstDayOfMonth, today, OrderStatusEnum.FINISHED.toString(), currentUserInfo.getUserId());
        }
        resultMap.put("thisMonthOrderCount", thisMonthResult.getData());

        //总销售额
        CommonResult salesVolumeThisMonthResult;
        if (currentUserInfo.getUserId() == 1) {
            salesVolumeThisMonthResult = feignBackOrderService.salesVolume(firstDayOfMonth, today,  OrderStatusEnum.FINISHED.toString(), null);
        } else {
            salesVolumeThisMonthResult = feignBackOrderService.salesVolume(firstDayOfMonth, today,  OrderStatusEnum.FINISHED.toString(),currentUserInfo.getUserId());
        }
        resultMap.put("salesVolumeThisMonth", salesVolumeThisMonthResult.getData());

        //近七日订单数
        String sevenDaysAgo = DateUtil.format(DateUtil.offsetDay(new Date(), -7),com.mall.common.base.utils.DateUtil.YYYY_DD_MM);
        CommonResult sevenDaysResult;
        if (currentUserInfo.getUserId() == 1) {
            sevenDaysResult = feignBackOrderService.count(sevenDaysAgo, today, OrderStatusEnum.FINISHED.toString(), null);
        } else {
            sevenDaysResult = feignBackOrderService.count(sevenDaysAgo, today, OrderStatusEnum.FINISHED.toString(), currentUserInfo.getUserId());
        }
        resultMap.put("lastSevenDaysOrderCount", sevenDaysResult.getData());

        //昨日订单数
        String yesterday = DateUtil.format(DateUtil.offsetDay(new Date(), -1),com.mall.common.base.utils.DateUtil.YYYY_DD_MM);
        CommonResult yesterdayResult;
        if (currentUserInfo.getUserId() == 1) {
            yesterdayResult = feignBackOrderService.count(yesterday, today, OrderStatusEnum.FINISHED.toString(), null);
        } else {
            yesterdayResult = feignBackOrderService.count(yesterday, today, OrderStatusEnum.FINISHED.toString(), currentUserInfo.getUserId());
        }
        resultMap.put("yesterdayOrderCount", yesterdayResult.getData());

        //今日订单数
        CommonResult todayResult;
        if (currentUserInfo.getUserId() == 1) {
            todayResult = feignBackOrderService.count(today, today, OrderStatusEnum.FINISHED.toString(), null);
        } else {
            todayResult = feignBackOrderService.count(today, today, OrderStatusEnum.FINISHED.toString(), currentUserInfo.getUserId());
        }
        resultMap.put("todayOrderCount", todayResult.getData());

        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), resultMap);
    }

    /**
     * 饼图数据
     * @return
     */
    @GetMapping("pieChartData")
    public CommonResult pieChartData() {
        CurrentUserInfo currentUserInfo = CurrentUserContextUtil.getCurrentUserInfo();
        CommonResult goodsListResult;
        if (currentUserInfo.getUserId() == 1) { //平台管理员账号，所有的
            goodsListResult = feignGoodsService.getGoodsCountByCategory(null);
        } else { //商户
            goodsListResult = feignGoodsService.getGoodsCountByCategory(currentUserInfo.getUserId());
        }
        if (goodsListResult == null) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), null);
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsListResult.getData());

    }

    /**
     * 近六个月的订单销量
     * @return
     */
    @GetMapping("barChartData")
    public CommonResult barChartData() {
        CurrentUserInfo currentUserInfo = CurrentUserContextUtil.getCurrentUserInfo();
        CommonResult orderCountResult;
        if (currentUserInfo.getUserId() == 1) { //管理员
            orderCountResult = feignBackOrderService.recentSixMonthOrderCountList(null);
        } else { //商户
            orderCountResult = feignBackOrderService.recentSixMonthOrderCountList(currentUserInfo.getUserId());
        }
        if (orderCountResult == null) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), null);
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), orderCountResult.getData());
    }
}
