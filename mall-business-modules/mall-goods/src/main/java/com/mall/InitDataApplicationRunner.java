package com.mall;

import com.mall.common.seata.util.RedisUtil;
import com.mall.goods.constants.RedisKeyConstant;
import com.mall.goods.entity.GoodsCategoryEntity;
import com.mall.goods.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 用于服务启动时向redis初始化一些数据
 * @Date 2021/7/5 10:04
 * @Version 1.0
 */
@Component
public class InitDataApplicationRunner implements ApplicationRunner {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (redisUtil.hGet(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY) == null) {
            List<GoodsCategoryEntity> goodsCategoryTree  = goodsCategoryService.queryGoodsCategoryTree(0L);
            redisUtil.hSet(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY, goodsCategoryTree);
        }
    }
}
