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
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
        //是否设为默认，如果是，则需要将其他模板设置为非默认（默认只能有一个）
        if (freightTemplateEntity.getIsDefault()) {
            setOtherIsNotDefault(freightTemplateEntity);
        }
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
        //是否设为默认，如果是，则需要将其他模板设置为非默认（默认只能有一个）
        if (freightTemplateEntity.getIsDefault()) {
            setOtherIsNotDefault(freightTemplateEntity);
        }
        this.updateById(freightTemplateEntity);
        //删除原来的默认运费，新增新的默认运费
        freightDefaultService.remove(new QueryWrapper<FreightDefaultEntity>()
                .eq("freight_template_id", freightTemplateEntity.getId()));
        if (freightTemplateEntity.getEnableDefaultFreight()) {//开启默认运费
            FreightDefaultEntity defaultEntity = freightTemplateEntity.getFreightDefaultEntity();
            if(defaultEntity != null){
                defaultEntity.setFreightTemplateId(freightTemplateEntity.getId());
                freightDefaultService.save(defaultEntity);
            }
        }
        //删除原来的运费规则，新增新的运费规则
        freightRuleService.remove(new QueryWrapper<FreightRuleEntity>()
                .eq("freight_template_id", freightTemplateEntity.getId()));
        List<FreightRuleEntity> freightRuleEntityList = freightTemplateEntity.getFreightRuleEntityList();
        if (!CollectionUtils.isEmpty(freightRuleEntityList)){
            freightRuleEntityList.stream().forEach(freightRuleEntity ->
                    freightRuleEntity.setFreightTemplateId(freightTemplateEntity.getId()));
            freightRuleService.saveBatch(freightRuleEntityList);
        }
        //删除原来的包邮规则，新增新的包邮规则
        freightFreeRuleService.remove(new QueryWrapper<FreightFreeRuleEntity>()
                .eq("freight_template_id", freightTemplateEntity.getId()));
        List<FreightFreeRuleEntity> freightFreeRuleEntityList = freightTemplateEntity.getFreightFreeRuleEntityList();
        if (!CollectionUtils.isEmpty(freightFreeRuleEntityList)) {
            freightFreeRuleEntityList.stream().forEach(freightFreeRuleEntity ->
                    freightFreeRuleEntity.setFreightTemplateId(freightTemplateEntity.getId()));
            freightFreeRuleService.saveBatch(freightFreeRuleEntityList);
        }
        return 1;
    }

    /**
     * 将其他运费模板置为非默认
     * @param freightTemplateEntity
     */
    private void setOtherIsNotDefault(FreightTemplateEntity freightTemplateEntity) {
        Long adminUserId = freightTemplateEntity.getAdminUserId();
        FreightTemplateEntity templateEntity = this.getOne(new QueryWrapper<FreightTemplateEntity>()
                .eq("admin_user_id", adminUserId).eq("is_default", true));
        if (templateEntity != null){
            templateEntity.setIsDefault(false);
            baseMapper.updateById(templateEntity);
        }
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
        wrapper.eq("admin_user_id",adminUserId);
        IPage<FreightTemplateEntity> iPage = baseMapper.getFreightTemplateEntityByPage(page, wrapper, adminUserId);
        return new PageUtil(iPage);
    }

    /**
     * 根据id获取运费模板信息
     * @param id
     * @return
     */
    @Override
    public FreightTemplateEntity getFreightTemplateById(Long id) {
        return baseMapper.getFreightTemplateById(id);
    }

    /**
     * 设为默认 / 取消默认
     * @param id
     * @param isDefault
     */
    @Override
    public void updateIsDefault(Long id, Boolean isDefault) {
        FreightTemplateEntity freightTemplateEntity = baseMapper.getFreightTemplateById(id);
        if (!isDefault){
            freightTemplateEntity.setIsDefault(false);
            this.updateById(freightTemplateEntity);
        }else {
            List<FreightTemplateEntity> updateList = new ArrayList<>();
            freightTemplateEntity.setIsDefault(true);
            updateList.add(freightTemplateEntity);
            //查询该商户下所有的发货地址，把原先默认的改为非默认
            Long adminUserId = freightTemplateEntity.getAdminUserId();
            FreightTemplateEntity templateEntity = this.getOne(new QueryWrapper<FreightTemplateEntity>()
                    .eq("admin_user_id", adminUserId).eq("is_default", true));
            if (templateEntity != null){
                templateEntity.setIsDefault(false);
                updateList.add(templateEntity);
            }
            this.saveOrUpdateBatch(updateList);
        }
    }

    /**
     * 删除模板（支持批量）
     * @param freightTemplateIds
     */
    @Override
    @Transactional
    public void deleteBatch(Long[] freightTemplateIds) {
        for (Long freightTemplateId : freightTemplateIds) {
            //删除默认运费
            freightDefaultService.remove(new QueryWrapper<FreightDefaultEntity>()
                    .eq("freight_template_id", freightTemplateId));
            //删除运费规则
            freightRuleService.remove(new QueryWrapper<FreightRuleEntity>()
                    .eq("freight_template_id", freightTemplateId));
            //删除包邮规则
            freightFreeRuleService.remove(new QueryWrapper<FreightFreeRuleEntity>()
                    .eq("freight_template_id", freightTemplateId));
            //删除模板
            baseMapper.deleteById(freightTemplateId);
        }
    }

    /**
     * 根据条件查询所有物流公司
     * @param params
     * @return
     */
    @Override
    public List<FreightTemplateEntity> getByParams(Map<String, Object> params) {
        //运费模板名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        List<FreightTemplateEntity> freightTemplateEntityList = this.list(
                new QueryWrapper<FreightTemplateEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name).
                        eq(adminUserId != null, "admin_user_id", adminUserId)
        );
        return freightTemplateEntityList;
    }
}
