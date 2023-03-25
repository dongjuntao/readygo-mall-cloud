package com.mall.search.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mall.common.base.CommonResult;
import com.mall.goods.api.FeignGoodsService;
import com.mall.search.entity.ESGoods;
import com.mall.search.service.ESGoodsService;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品信息 elasticsearch
 * @Date 2023/3/10 16:20
 * @Version 1.0
 */
@RestController
@RequestMapping("es/goods")
public class ESGoodsController {

    @Autowired
    private FeignGoodsService feignGoodsService;

    @Autowired
    private ESGoodsService esGoodsService;

    /**
     * 商品信息全量保存到 elasticsearch
     */
    @PostMapping("/insertAll")
    public void insertAll() {
        CommonResult commonResult = feignGoodsService.allGoodsWithDetail();
        if (commonResult != null && "200".equals(commonResult.getCode())) {
            List goodsList = (List)commonResult.getData();
            List<ESGoods> newGoodsList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(goodsList)) {
                for (int i=0; i<goodsList.size(); i++) {
                    Map<String, Object> map = (Map<String,Object>) goodsList.get(i);
                    ESGoods newEsGoods = BeanUtil.mapToBean(map, ESGoods.class, true);
                    newGoodsList.add(newEsGoods);
                }
            }
            esGoodsService.saveBatch(newGoodsList);
        }
    }

    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params) {
        Page<ESGoods> page = esGoodsService.list(params);
        return CommonResult.success(page);
    }

    @PutMapping("/update")
    public CommonResult update(ESGoods esGoods) {
        esGoodsService.update(esGoods);
        return CommonResult.success();
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        esGoodsService.delete(id);
        return CommonResult.success();
    }
}
