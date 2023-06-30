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
    public Page<ESGoods> list(Integer pageNum,
                              Integer pageSize,
                              Integer sortType,
                              String searchValue,
                              String categories) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //分页
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        queryBuilder.withPageable(pageable);

        //排序
        if (sortType == null) {
            //按相关度
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else if (sortType == 0) { //综合
            queryBuilder.withSort(SortBuilders.fieldSort("totalSales").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.fieldSort("minPrice").order(SortOrder.ASC));
            queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else if (sortType == 1) { //商品新旧
            queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        } else if (sortType == 2) { //销量
            queryBuilder.withSort(SortBuilders.fieldSort("totalSales").order(SortOrder.DESC));
        } else if (sortType == 3) { //单价（降序）
            queryBuilder.withSort(SortBuilders.fieldSort("minPrice").order(SortOrder.DESC));
        } else if (sortType == 4) { //单价（升序）
            queryBuilder.withSort(SortBuilders.fieldSort("minPrice").order(SortOrder.ASC));
        }

        //查询关键字
        if (!StringUtils.isEmpty(searchValue)) {
            //构造类型查询条件
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            BoolQueryBuilder shouldBoolQueryBuilder = QueryBuilders.boolQuery();
            shouldBoolQueryBuilder.should(QueryBuilders.matchQuery("name", searchValue));
            shouldBoolQueryBuilder.should(QueryBuilders.matchQuery("description", searchValue));
            boolQueryBuilder.must(shouldBoolQueryBuilder);
            queryBuilder.withQuery(boolQueryBuilder);
        }
        //商品分类
        if (!StringUtils.isEmpty(categories)) {
            String[] categoryArr = categories.split(",");
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (categoryArr.length == 1) { //只有一级分类
                boolQueryBuilder.must(QueryBuilders.termQuery("goodsCategoryIdFirst", categoryArr[0]));
            } else if (categoryArr.length == 2) { //只有二级分类
                boolQueryBuilder.must(QueryBuilders.termQuery("goodsCategoryIdFirst", categoryArr[0]));
                boolQueryBuilder.must(QueryBuilders.termQuery("goodsCategoryIdSecond", categoryArr[1]));
            } else if (categoryArr.length == 3) { //有三级分类
                boolQueryBuilder.must(QueryBuilders.termQuery("goodsCategoryIdFirst", categoryArr[0]));
                boolQueryBuilder.must(QueryBuilders.termQuery("goodsCategoryIdSecond", categoryArr[1]));
                boolQueryBuilder.must(QueryBuilders.termQuery("goodsCategoryIdThird", categoryArr[2]));
            }
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

}
