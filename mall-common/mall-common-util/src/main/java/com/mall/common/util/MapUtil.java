package com.mall.common.util;

import java.util.HashMap;

/**
 * @Author DongJunTao
 * @Description 增强型Map
 * @Date 2021/8/16 21:36
 * @Version 1.0
 */
public class MapUtil extends HashMap<String,Object> {

    @Override
    public MapUtil put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
