package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.ExpressSettingEntity;
import com.mall.admin.entity.ShippingInfoEntity;
import com.mall.admin.mapper.ExpressSettingMapper;
import com.mall.admin.service.ExpressSettingService;
import com.mall.common.util.MapUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void updateIsDefault(Long logisticsCompanyId, Long adminUserId, Boolean isDefault) {
        ExpressSettingEntity expressSettingEntity = baseMapper.selectOne(
                new QueryWrapper<ExpressSettingEntity>()
                        .eq(logisticsCompanyId != null, "logistics_company_id", logisticsCompanyId)
                        .eq(adminUserId != null, "admin_user_id", adminUserId)
        );
        if (!isDefault){
            expressSettingEntity.setIsDefault(false);
            this.updateById(expressSettingEntity);
        } else {
            expressSettingEntity.setIsDefault(true);
            List<ExpressSettingEntity> updateList = new ArrayList<>();
            updateList.add(expressSettingEntity);
            Long belongAdminUserId = expressSettingEntity.getAdminUserId();//查到所属商家
            ExpressSettingEntity expressSetting = this.getOne(
                    new QueryWrapper<ExpressSettingEntity>()
                            .eq("admin_user_id", belongAdminUserId)
                            .eq("is_default", true));
            if(expressSetting != null){
                expressSetting.setIsDefault(false);
                updateList.add(expressSetting);
            }
            this.saveOrUpdateBatch(updateList);
        }
    }
}
