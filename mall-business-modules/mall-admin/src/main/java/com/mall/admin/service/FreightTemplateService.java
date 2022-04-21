package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.FreightTemplateEntity;
import com.mall.common.base.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 运费模板service
 * @Date 2021/9/17 22:58
 * @Version 1.0
 */
public interface FreightTemplateService extends IService<FreightTemplateEntity> {

    /**
     * 新增运费模板
     * @param freightTemplateEntity
     * @return
     */
    int saveFreightTemplate(FreightTemplateEntity freightTemplateEntity);

    /**
     * 修改运费模板
     * @param freightTemplateEntity
     * @return
     */
    int updateFreightTemplate(FreightTemplateEntity freightTemplateEntity);

    /**
     * 分页查询运费模板
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 根据条件查询所有运费模板
     * @param params
     * @return
     */
    List<FreightTemplateEntity> getByParams(Map<String, Object> params);

    FreightTemplateEntity getFreightTemplateById(Long id);

    /**
     * 取消默认，设为默认
     * @param id
     * @param isDefault
     */
    void updateIsDefault(Long id, Boolean isDefault);

    /**
     * 删除运费模板（支持批量删除）
     * @param freightTemplateIds
     */
    void deleteBatch(Long[] freightTemplateIds);

}
