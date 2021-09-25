package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.FreightRuleEntity;
import com.mall.admin.mapper.FreightRuleMapper;
import com.mall.admin.service.FreightRuleService;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description 运费规则service实现
 * @Date 2021/9/17 23:39
 * @Version 1.0
 */
@Service("freightRuleService")
public class FreightRuleServiceImpl extends ServiceImpl<FreightRuleMapper, FreightRuleEntity>
        implements FreightRuleService {
}
