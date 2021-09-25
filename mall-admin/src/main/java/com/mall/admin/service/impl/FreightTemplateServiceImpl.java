package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.*;
import com.mall.admin.mapper.FreightTemplateMapper;
import com.mall.admin.service.FreightDefaultService;
import com.mall.admin.service.FreightFreeRuleService;
import com.mall.admin.service.FreightRuleService;
import com.mall.admin.service.FreightTemplateService;
import com.mall.common.util.PageBuilder;
import com.mall.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 运费模板service实现
 * @Date 2021/9/17 23:05
 * @Version 1.0
 */
@Service("freightTemplateService")
public class FreightTemplateServiceImpl extends ServiceImpl<FreightTemplateMapper, FreightTemplateEntity>
        implements FreightTemplateService {

    @Autowired
    private FreightDefaultService freightDefaultService;

    @Autowired
    private FreightRuleService freightRuleService;

    @Autowired
    private FreightFreeRuleService freightFreeRuleService;

    /**
     * 新增运费模板
     * @param freightTemplateEntity
     * @return
     */
    @Override
    @Transactional
    public int saveFreightTemplate(FreightTemplateEntity freightTemplateEntity) {
        //运费模板保存
        freightTemplateEntity.setCreateTime(new Date());
        this.save(freightTemplateEntity);
        Integer type = freightTemplateEntity.getType();
        //只有买家承担运费时才需要设置默认运费，运费规则，包邮规则
        if (type == 0){
            //保存默认运费
            FreightDefaultEntity defaultEntity = freightTemplateEntity.getFreightDefaultEntity();
            if (defaultEntity != null){
                defaultEntity.setFreightTemplateId(freightTemplateEntity.getId());
                freightDefaultService.save(defaultEntity);
            }
            //保存运费规则
            List<FreightRuleEntity> freightRuleEntityList = freightTemplateEntity.getFreightRuleEntityList();
            if (!CollectionUtils.isEmpty(freightRuleEntityList)){
                freightRuleEntityList.stream().forEach(freightRuleEntity ->
                        freightRuleEntity.setFreightTemplateId(freightTemplateEntity.getId()));
                freightRuleService.saveBatch(freightRuleEntityList);
            }
            //保存包邮规则
            List<FreightFreeRuleEntity> freightFreeRuleEntityList = freightTemplateEntity.getFreightFreeRuleEntityList();
            if (!CollectionUtils.isEmpty(freightFreeRuleEntityList)) {
                freightFreeRuleEntityList.stream().forEach(freightFreeRuleEntity ->
                        freightFreeRuleEntity.setFreightTemplateId(freightTemplateEntity.getId()));
                freightFreeRuleService.saveBatch(freightFreeRuleEntityList);
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public int updateFreightTemplate(FreightTemplateEntity freightTemplateEntity) {
        //修改运费模板
        freightTemplateEntity.setUpdateTime(new Date());
        this.updateById(freightTemplateEntity);
        //删除原来的默认运费，新增新的默认运费
        freightDefaultService.remove(new QueryWrapper<FreightDefaultEntity>()
                .eq("freight_template_id", freightTemplateEntity.getId()));
        if (freightTemplateEntity.getEnableDefaultFreight()) {//开启默认运费
            FreightDefaultEntity defaultEntity = freightTemplateEntity.getFreightDefaultEntity();
            freightDefaultService.save(defaultEntity);
        }
        //删除原来的运费规则，新增新的运费规则
        freightRuleService.remove(new QueryWrapper<FreightRuleEntity>()
                .eq("freight_template_id", freightTemplateEntity.getId()));
        List<FreightRuleEntity> freightRuleEntityList = freightTemplateEntity.getFreightRuleEntityList();
        if (!CollectionUtils.isEmpty(freightRuleEntityList)){
            freightRuleService.saveBatch(freightRuleEntityList);
        }
        //删除原来的包邮规则，新增新的包邮规则
        freightFreeRuleService.remove(new QueryWrapper<FreightFreeRuleEntity>()
                .eq("freight_template_id", freightTemplateEntity.getId()));
        List<FreightFreeRuleEntity> freightFreeRuleEntityList = freightTemplateEntity.getFreightFreeRuleEntityList();
        if (!CollectionUtils.isEmpty(freightFreeRuleEntityList)) {
            freightFreeRuleService.saveBatch(freightFreeRuleEntityList);
        }
        return 1;
    }

    /**
     * 分页查询运费模板
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        Page<FreightTemplateEntity> page = (Page<FreightTemplateEntity>)new PageBuilder<FreightTemplateEntity>()
                .getPage(params);
        QueryWrapper<FreightTemplateEntity> wrapper = new QueryWrapper<>();
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        IPage<FreightTemplateEntity> iPage = baseMapper.getFreightTemplateEntityByPage(page, wrapper, adminUserId);
        return new PageUtil(iPage);
    }
}
