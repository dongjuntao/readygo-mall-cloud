package com.mall.goods.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2023/3/6 15:47
 * @Version 1.0
 */
@Component
public class JobTest {

    /**
     * 简单任务示例（Bean模式）
     */
    @XxlJob("myJobHandler")
    public void mileageJobHandler() throws Exception {
        System.out.println("running...");
    }
}
