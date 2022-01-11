package com.mall.seckill.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.util.PageUtil;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀商品controller
 * @Date 2022/1/7 15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("seckillGoods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsSkuService seckillGoodsSkuService;

    /**
     * 秒杀商品详细信息（包含商品sku信息）
     */
    @GetMapping("/sku/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        List<GoodsSkuVO> goodsSkuVOList = seckillGoodsSkuService.getGoodsSkuListById(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsSkuVOList);
    }
}
