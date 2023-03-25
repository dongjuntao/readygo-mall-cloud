package com.mall.search.service.impl;

import com.mall.common.base.utils.PageUtil;
import com.mall.search.entity.ESGoods;
import com.mall.search.service.ESGoodsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.NestedSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author DongJunTao
 * @Description es操作商品信息
 * @Date 2023/3/15 15:37
 * @Version 1.0
 */
@Service
public class ESGoodsServiceImpl implements ESGoodsService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void saveBatch(List<ESGoods> esGoodsList) {
        elasticsearchRestTemplate.save(esGoodsList);
    }

    @Override
    public void save(ESGoods esGoods) {
        elasticsearchRestTemplate.save(esGoods);
    }

    @Override
    public void update(ESGoods esGoods) {
        elasticsearchRestTemplate.save(esGoods);
    }

    @Override
    public Page<ESGoods> list(Map<String, Object> params) {
        Integer pageNum = params.get("pageNum") == null ? null: Integer.valueOf(params.get("pageNum").toString());
        Integer pageSize = params.get("pageSize") == null ? null: Integer.valueOf(params.get("pageSize").toString());
        Integer sortType = params.get("sortType") == null ? null: Integer.valueOf(params.get("sortType").toString());//排序类型
        String keyword = params.get("keyword") == null ? null: params.get("keyword").toString();//搜索关键字

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //分页
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        queryBuilder.withPageable(pageable);
        //排序
        if (sortType == null) {
            //按相关度
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else if (sortType == 0) { //综合
            queryBuilder.withSort(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        } else if (sortType == 1) { //商品新旧
            queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        } else if (sortType == 2) { //销量
            queryBuilder.withSort(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
        } else if (sortType == 3) { //单价
            queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        }
        //查询关键字
        if (!StringUtils.isEmpty(keyword)) {
            //构造类型查询条件
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            BoolQueryBuilder shouldBoolQueryBuilder = QueryBuilders.boolQuery();
            shouldBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("name", keyword));
            shouldBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("description", keyword));
            boolQueryBuilder.must(shouldBoolQueryBuilder);
            queryBuilder.withQuery(boolQueryBuilder);
        }
        NativeSearchQuery query = queryBuilder.build();
        SearchHits<ESGoods> searchHits = elasticsearchRestTemplate.search(query, ESGoods.class);
        if(searchHits.getTotalHits()<=0){
            return new PageImpl<>(new ArrayList<>(),pageable,0);
        }
        List<ESGoods> searchList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        return new PageImpl<>(searchList,pageable,searchHits.getTotalHits());
    }

    @Override
    public void delete(Long id) {
        elasticsearchRestTemplate.delete(String.valueOf(id), ESGoods.class);
    }
}
