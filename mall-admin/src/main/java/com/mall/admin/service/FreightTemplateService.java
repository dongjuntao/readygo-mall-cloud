package com.mall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.admin.entity.FreightTemplateEntity;
import com.mall.common.util.PageUtil;

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
}
