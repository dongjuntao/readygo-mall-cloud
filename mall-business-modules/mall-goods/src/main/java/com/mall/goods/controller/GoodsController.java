package com.mall.goods.controller;

import com.mall.common.base.CommonResult;
import com.mall.goods.entity.GoodsCategoryEntity;
import com.mall.goods.entity.GoodsEntity;
import com.mall.goods.entity.GoodsSkuEntity;
import com.mall.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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

    /**
     * 新增商品
     * @param goodsEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody GoodsEntity goodsEntity) {
        //保存商品
        goodsEntity.setCreateTime(new Date());
        goodsService.saveOrUpdate(goodsEntity);
        List<GoodsSkuEntity> goodsList = goodsEntity.getGoodsSkuList();
        if (goodsList != null && goodsList.size()>0) {

        }
        return CommonResult.success();
    }
}
