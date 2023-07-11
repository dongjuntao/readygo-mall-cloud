package com.mall.goods.api;

import com.mall.common.base.CommonResult;
import com.mall.common.base.config.FeignConfig;
import com.mall.common.base.constant.ServiceNameConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2022/1/6 13:23
 * @Version 1.0
 */
@FeignClient(value = ServiceNameConstant.MALL_GOODS,configuration = FeignConfig.class)
@RequestMapping(value = "goods")
public interface FeignGoodsService {

    /**
     * 通过id获取商品信息
     * @param id
     * @return
     */
    @GetMapping("/getGoodsById")
    CommonResult getGoodsById(@RequestParam("id") Long id);

    /**
     * 根据ids集合所有商品列表（不分页）
     */
    @GetMapping("/listByIds")
    CommonResult listByIds(@RequestParam Long[] ids);

    /**
     * 全部商品（包括关联的详细信息）
     * @return
     */
    @GetMapping("/allGoodsWithDetail")
    CommonResult allGoodsWithDetail();

    /**
     * 根据参数获取订单信息（包括订单明细）
     * @return
     */
    @GetMapping("count")
    CommonResult count(@RequestParam(value = "goodsStatus", required = false) String goodsStatus,
                       @RequestParam(value = "adminUserId", required = false) Long adminUserId);

    /**
     * 商品数量统计
     * @return
     */
    @GetMapping("getGoodsCountByCategory")
    CommonResult getGoodsCountByCategory(@RequestParam(value = "adminUserId",required = false) Long adminUserId);

}
