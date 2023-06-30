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
    private ESGoodsService esGoodsService;

    /**
     * 从ES中查询商品信息
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param sortType 排序方式【0:综合 1:新品 2:销量 3:单价（降序） 4:单价（升序）】
     * @param searchValue
     * @param categories
     * @return
     */
    @GetMapping("search")
    public CommonResult search(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "sortType",required = false) Integer sortType,
                             @RequestParam(value = "searchValue",required = false) String searchValue,
                             @RequestParam(value = "categories",required = false) String categories) {
        Page<ESGoods> page = esGoodsService.list(pageNum,pageSize,sortType,searchValue,categories);
        return CommonResult.success(page);
    }

}
