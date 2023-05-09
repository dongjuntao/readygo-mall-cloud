package com.mall.member.job;

import com.mall.member.entity.CouponReceivedEntity;
import com.mall.member.service.CouponReceivedService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 已领取的优惠券到期后自动刷新状态
 * @Date 2023/3/6 15:47
 * @Version 1.0
 */
@Component
public class AutoRefreshReceivedCouponUseStatusTask {

    @Autowired
    private CouponReceivedService couponReceivedService;

    /**
     * 简单任务示例（Bean模式）
     */
    @XxlJob("autoRefreshReceivedCouponUseStatusHandler")
    public void autoRefreshReceivedCouponUseStatusHandler() throws Exception {
        List<CouponReceivedEntity> couponReceivedList = couponReceivedService.getListAll(new HashMap<>());
        if (!CollectionUtils.isEmpty(couponReceivedList)) {
            List<CouponReceivedEntity> overdueList = new ArrayList<>();
            for (CouponReceivedEntity couponReceived : couponReceivedList) {
                Date end = couponReceived.getValidPeriodEnd();
                //如果结束时间小于当前时间，则表示过期，修改状态
                if (end.compareTo(new Date()) < 0) {
                    couponReceived.setUseStatus(2); //已过期
                    overdueList.add(couponReceived);
                }
            }
            //批量修改
            couponReceivedService.saveOrUpdateBatch(overdueList);
        }
    }
}
