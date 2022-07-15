package com.mall.goods.controller.front;

import com.mall.common.base.CommonResult;
import com.mall.common.base.dto.CurrentUserInfo;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.CurrentUserContextUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.entity.GoodsSkuEntity;
import com.mall.goods.service.GoodsService;
import com.mall.goods.service.GoodsSkuService;
import com.mall.goods.vo.ReduceStockVO;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description 商品展示【供商城门户端使用】
 * @Date 2022/3/25 21:27
 * @Version 1.0
 */
@RestController
@RequestMapping("front/goods")
public class FrontGoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    /**
     * 商品信息
     */
    @GetMapping("getGoodsById")
    public CommonResult getGoodsById(@RequestParam("id") Long id){
        GoodsEntity goodsEntity = goodsService.getGoodsAndSku(id);
        return CommonResult.success(goodsEntity);
    }

    /**
     * 商品列表（分页）
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil pageResult = goodsService.queryPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
    }

    /**
     * 根据商品ids集合查询商品【包含sku信息】
     * 商品列表（分页）
     */
    @GetMapping("/listByIds")
    public CommonResult listByIds(@RequestParam Long[] ids){
        List<GoodsEntity> goodsList = goodsService.getByGoodsIds(ids);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsList);
    }

    /**
     * 根据skuIds获取sku列表信息
     * @param ids
     * @return
     */
    @GetMapping("/getGoodsSkuList")
    public CommonResult getGoodsSkuList(@RequestParam Long[] ids){
        List<GoodsSkuEntity> goodsSkuList = goodsSkuService.getGoodsSkuList(ids);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsSkuList);
    }

    /**
     * 库存扣减
     */
    @PutMapping("reduceStock")
    public CommonResult reduceStock(@RequestBody ReduceStockVO reduceStock){
        goodsSkuService.reduceStock(reduceStock);
        return CommonResult.success();
    }

    /**
     * 库存扣减（批量）
     */
    @PostMapping(value = "batchReduceStock", consumes = "application/json", produces = "application/json")
    public CommonResult batchReduceStock(@RequestBody ArrayList<Map<String,Object>> reduceStockList){
        goodsSkuService.batchReduceStock(reduceStockList);
        return CommonResult.success();
    }

}
