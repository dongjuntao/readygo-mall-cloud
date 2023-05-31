package com.mall.goods.controller;

import com.mall.admin.api.feign.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.entity.GoodsSkuEntity;
import com.mall.goods.service.FrontGoodsService;
import com.mall.goods.service.GoodsSkuService;
import com.mall.goods.vo.ReduceStockVO;
import com.mall.seckill.api.feign.FeignSeckillConfigService;
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
    private FrontGoodsService frontGoodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    @Autowired
    private FeignSeckillConfigService feignSeckillConfigService;

    /**
     * 商品信息（包括sku）
     */
    @GetMapping("getGoodsById")
    public CommonResult getGoodsById(@RequestParam("id") Long id){
        GoodsEntity goodsEntity = frontGoodsService.getGoodsAndSku(id);
        if (goodsEntity == null) {
            return CommonResult.fail(ResultCodeEnum.GOODS_NOT_EXIST.getCode(), ResultCodeEnum.GOODS_NOT_EXIST.getMessage());
        }
        CommonResult result = feignAdminUserService.getAdminUserById(goodsEntity.getAdminUserId());
        if ("200".equals(result.getCode()) && result.getData() != null) {
            Map<String,Object> resultMap = (Map<String, Object>)result.getData();
            goodsEntity.setMerchantName(String.valueOf(resultMap.get("name"))); //商户名称
        }
        //查看是否参与了秒杀，如果有，需要填入秒杀信息
//        feignSeckillConfigService.getById()
        return CommonResult.success(goodsEntity);
    }

    /**
     * 商品列表（分页）
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                             @RequestParam(value = "pageSize",required = false) Integer pageSize,
                             @RequestParam(value = "name",required = false) String name,
                             @RequestParam(value = "adminUserId",required = false) Long adminUserId,
                             @RequestParam(value = "categoryIds",required = false) String categoryIds){
        PageUtil pageResult = frontGoodsService.queryPage(pageNum,pageSize,name,adminUserId,categoryIds);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
    }

    /**
     * 根据商品ids集合查询商品【包含sku信息】
     * 商品列表（分页）
     */
    @GetMapping("/listByIds")
    public CommonResult listByIds(@RequestParam Long[] ids){
        List<GoodsEntity> goodsList = frontGoodsService.getByGoodsIds(ids);
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
