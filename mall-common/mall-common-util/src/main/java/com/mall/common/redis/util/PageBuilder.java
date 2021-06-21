package com.mall.common.redis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/6/1 15:18
 * @Version 1.0
 */
public class PageBuilder<T> {

    private static String PAGE_NUM = "pageNum";

    private static String PAGE_SIZE = "pageSize";

    public IPage<T> getPage(Map<String, Object> params) {
        long pageNum = 1;
        long pageSize = 10;
        if (params.get(PAGE_NUM) != null) {
            pageNum = Long.parseLong(String.valueOf(params.get(PAGE_NUM)));
        }
        if (params.get(PAGE_SIZE) != null) {
            pageSize = Long.parseLong(String.valueOf(params.get(PAGE_SIZE)));
        }
        Page<T> page = new Page<>(pageNum, pageSize);
        return page;
    }
}
