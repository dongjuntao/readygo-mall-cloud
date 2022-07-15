package com.mall.order.util;

import cn.hutool.core.util.IdUtil;
import com.mall.order.enums.CodePrefixEnum;

/**
 * @Author DongJunTao
 * @Description 订单号生成工具
 * @Date 2022/6/16 16:12
 * @Version 1.0
 */
public class SnowFlakeUtil {

    /**
     * 订单号生成
     * @param predixEnum
     * @param workerId
     * @param datacenterId
     * @return
     */
    public static String getSnowFlakeId(CodePrefixEnum predixEnum, long workerId, long datacenterId) {
        return predixEnum.name() + IdUtil.getSnowflake(workerId,datacenterId).nextId();
    }
}
