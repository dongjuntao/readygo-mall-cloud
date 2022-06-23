package com.mall.order.util;

import cn.hutool.core.util.IdUtil;

/**
 * @Author DongJunTao
 * @Description 订单号生成工具
 * @Date 2022/6/16 16:12
 * @Version 1.0
 */
public class SnowFlakeUtil {

    /**
     * 订单号生成
     * @param predix
     * @param workerId
     * @param datacenterId
     * @return
     */
    public static String getSnowFlakeId(String predix, long workerId, long datacenterId) {
        return predix + IdUtil.getSnowflake(workerId,datacenterId).nextId();
    }
}
