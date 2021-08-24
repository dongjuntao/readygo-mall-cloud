package com.mall.goods.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.util.PageUtil;
import com.mall.goods.entity.*;
import com.mall.goods.service.GoodsService;
import com.mall.goods.service.GoodsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description 商品管理
 * @Date 2021/6/25 16:27
 * @Version 1.0
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSkuService goodsSkuService;

    /**
     * 新增商品
     * @param goodsEntity
     * @return
     */
    @PostMapping("save")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult save(@RequestBody GoodsEntity goodsEntity) {
        //保存商品
        goodsEntity.setCreateTime(new Date());
        goodsService.saveOrUpdate(goodsEntity);
        List<GoodsSkuEntity> goodsSkuList = goodsEntity.getGoodsSkuList();
        if (goodsSkuList != null && goodsSkuList.size()>0) {
            goodsSkuList.forEach(goodsSku->{
                goodsSku.setGoodsId(goodsEntity.getId());//设置sku的商品id
            });
            //新增商品sku
            goodsSkuService.saveBatch(goodsSkuList);
        }
        return CommonResult.success();
    }

    /**
     * 修改商品
     * @param goodsEntity
     * @return
     */
    @PutMapping("update")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(@RequestBody GoodsEntity goodsEntity) {
        goodsEntity.setUpdateTime(new Date());
        //查找原来的商品sku
        GoodsEntity old = goodsService.getGoodsAndSku(goodsEntity.getId());
        List<GoodsSkuEntity> oldSkuList = old.getGoodsSkuList();
        List<Long> skuIdList = new ArrayList<>();
        if (oldSkuList != null && oldSkuList.size()>0) {
            oldSkuList.forEach(sku->{ skuIdList.add(sku.getId()); });
            //删除旧的商品sku信息
            goodsSkuService.deleteBatch(skuIdList);
        }
        //保存商品
        goodsService.saveOrUpdate(goodsEntity);
        //sku表中的商品id
        List<GoodsSkuEntity> skuList = goodsEntity.getGoodsSkuList();
        if (skuList != null && skuList.size()>0) {
            skuList.forEach(sku->{sku.setGoodsId(goodsEntity.getId());});
        }
        //保存商品sku
        goodsSkuService.saveBatch(skuList);
        return CommonResult.success();
    }


    /**
     * 规格信息
     */
    @GetMapping("getGoodsById")
    public CommonResult info(@RequestParam("id") Long id){
        GoodsEntity goodsEntity = goodsService.getGoodsAndSku(id);
        return CommonResult.success(goodsEntity);
    }

    /**
     * 商品列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil pageResult = goodsService.queryPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
    }

    /**
     * 删除商品
     */
    @DeleteMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delete(@RequestBody Long[] ids){
        for (Long id : ids) {
            //删除关联的商品规格详细信息
            Map<String, Object> map = new HashMap<>();
            map.put("goods_id", id);
            goodsSkuService.removeByMap(map);
            //删除商品规格
            goodsService.removeById(id);
        }
        return CommonResult.success();
    }
}
