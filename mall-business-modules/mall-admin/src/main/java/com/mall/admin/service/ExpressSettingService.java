package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.ExpressSettingEntity;
import com.mall.admin.entity.LogisticsCompanyEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author DongJunTao
 * @Description 快递设置service
 * @Date 2021/9/11 16:30
 * @Version 1.0
 */
public interface ExpressSettingService extends IService<ExpressSettingEntity> {
    /**
     * 删除快递设置记录
     * @param expressSettingId
     * @param adminUserId
     */
    void delete(Long expressSettingId, Long adminUserId);

    /**
     * 保存快递设置记录
     * @param expressSettingEntity
     * @return
     */
    int saveExpressSetting(ExpressSettingEntity expressSettingEntity);

    void updateIsDefault(Long logisticsCompanyId, Long adminUserId, Boolean isDefault);
}
