package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.ExpressSettingEntity;
import com.mall.admin.mapper.ExpressSettingMapper;
import com.mall.admin.service.ExpressSettingService;
import com.mall.common.util.MapUtil;
import org.springframework.stereotype.Service;

/**
 * @Author DongJunTao
 * @Description 快递设置service实现类
 * @Date 2021/9/11 16:31
 * @Version 1.0
 */
@Service("expressSettingService")
public class ExpressSettingServiceImpl extends ServiceImpl<ExpressSettingMapper, ExpressSettingEntity>
        implements ExpressSettingService {

    @Override
    public void delete(Long logisticsCompanyId, Long adminUserId) {
        baseMapper.deleteByMap(new MapUtil()
                .put("logistics_company_id", logisticsCompanyId)
                .put("admin_user_id", adminUserId));
    }

    @Override
    public int saveExpressSetting(ExpressSettingEntity expressSettingEntity) {
        return baseMapper.insert(expressSettingEntity);
    }
}
