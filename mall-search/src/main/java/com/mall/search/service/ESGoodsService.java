package com.mall.search.service;

import com.mall.search.entity.ESGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description es操作商品信息
 * @Date 2023/3/15 15:28
 * @Version 1.0
 */
public interface ESGoodsService {

    Page<ESGoods> list(Integer pageNum,
                       Integer pageSize,
                       Integer sortType,
                       String searchValue,
                       String categories);
}
