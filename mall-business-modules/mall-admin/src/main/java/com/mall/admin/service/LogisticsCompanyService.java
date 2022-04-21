package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.LogisticsCompanyEntity;
import com.mall.common.base.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/9/7 21:23
 * @Version 1.0
 */
public interface LogisticsCompanyService extends IService<LogisticsCompanyEntity> {

    /**
     * 根据主键id获取物流公司实体
     * @param id
     * @return
     */
    LogisticsCompanyEntity getLogisticsCompanyById(long id);

    /**
     * 根据参数查询物流公司实体
     * @param params
     * @return
     */
    LogisticsCompanyEntity getLogisticsCompanyByParams(Map<String, Object> params);

    /**
     * 分页查询所有物流公司
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 分页查询所有物流公司（与express_setting联合查询）
     * @param params
     * @return
     */
    PageUtil getWithExpressSettingByPage(Map<String, Object> params);

    /**
     * 根据条件查询所有物流公司
     * @param params
     * @return
     */
    List<LogisticsCompanyEntity> getByParams(Map<String, Object> params);

    /**
     * 删除物流公司
     * @param companyIds
     */
    void deleteBatch(Long[] companyIds);

    /**
     * 保存物流公司
     * @param logisticsCompanyEntity
     * @return
     */
    int saveLogisticsCompany(LogisticsCompanyEntity logisticsCompanyEntity);

    /**
     * 修改物流公司
     * @param logisticsCompanyEntity
     * @return
     */
    int updateLogisticsCompany(LogisticsCompanyEntity logisticsCompanyEntity);
}
