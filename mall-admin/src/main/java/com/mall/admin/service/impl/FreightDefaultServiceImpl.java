package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.FreightDefaultEntity;
import com.mall.admin.mapper.FreightDefaultMapper;
import com.mall.admin.service.FreightDefaultService;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description 默认运费service实现
 * @Date 2021/9/17 23:49
 * @Version 1.0
 */
@Service("freightDefaultService")
public class FreightDefaultServiceImpl extends ServiceImpl<FreightDefaultMapper, FreightDefaultEntity>
        implements FreightDefaultService {
}
