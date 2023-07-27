package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.LogisticsCompanyEntity;
import com.mall.admin.mapper.LogisticsCompanyMapper;
import com.mall.admin.service.LogisticsCompanyService;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description
 * @Date 2021/9/7 21:29
 * @Version 1.0
 */
@Service("logisticsCompanyService")
public class LogisticsCompanyServiceImpl extends ServiceImpl<LogisticsCompanyMapper, LogisticsCompanyEntity>
        implements LogisticsCompanyService {

    /**
     * 根据主键id获取物流公司实体
     * @param id
     * @return
     */
    @Override
    public LogisticsCompanyEntity getLogisticsCompanyById(long id) {
        return this.getById(id);
    }

    /**
     * 根据参数查询物流公司实体
     * @param params
     * @return
     */
    @Override
    public LogisticsCompanyEntity getLogisticsCompanyByParams(Map<String, Object> params) {
        //物流公司名称
        String name = params.get("name") == null ? null : params.get("name").toString();
        //物流公司简称
        String abbreviation = params.get("abbreviation") == null ? null : params.get("abbreviation").toString();
        //物流公司编码
        String code = params.get("code") == null ? null : params.get("code").toString();
        Long id = params.get("id") == null ? null: Long.valueOf((params.get("id").toString()));
        return baseMapper.selectOne(
                new QueryWrapper<LogisticsCompanyEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .like(StringUtils.isNotBlank(code), "code", code)
                        .like(StringUtils.isNotBlank(abbreviation), "abbreviation", abbreviation)
                        .ne(id != null, "id",id));//排除id
    }

    /**
     * 分页查询所有物流公司
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param name 物流公司名称
     * @param abbreviation 物流公司简称
     * @param code 物流公司编码
     * @return
     */
    @Override
    public PageUtil getByPage(Integer pageNum, Integer pageSize, String name, String abbreviation, String code) {
        Map<String, Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        IPage<LogisticsCompanyEntity> page = this.page(
                new PageBuilder<LogisticsCompanyEntity>().getPage(pageParams),
                new QueryWrapper<LogisticsCompanyEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .like(StringUtils.isNotBlank(abbreviation), "abbreviation", abbreviation)
                        .like(StringUtils.isNotBlank(code), "code", code)
                        .orderByAsc("order_num")
        );
        return new PageUtil(page);
    }

    /**
     * 根据条件查询所有物流公司
     * @param name 物流公司名称
     * @param abbreviation 物流公司简称
     * @param code 物流公司编码
     * @return
     */
    @Override
    public List<LogisticsCompanyEntity> getByParams(String name, String abbreviation, String code, Boolean enable, Long adminUserId) {
        return baseMapper.getAllWithExpressSetting(name, abbreviation, code, enable, adminUserId);
    }

    /**
     * 删除物流公司
     * @param companyIds
     */
    @Override
    public void deleteBatch(Long[] companyIds) {
        this.removeByIds(Arrays.asList(companyIds));
    }

    /**
     * 保存物流公司
     * @param logisticsCompanyEntity
     * @return
     */
    @Override
    public int saveLogisticsCompany(LogisticsCompanyEntity logisticsCompanyEntity) {
        //先判断是否有重复的
        LogisticsCompanyEntity logisticsCompany = this.getLogisticsCompanyByParams(new MapUtil()
                .put("name",logisticsCompanyEntity.getName())
                .put("code", logisticsCompanyEntity.getCode()));
        if (logisticsCompany != null) {
            return -1;
        }
        logisticsCompanyEntity.setCreateTime(new Date());
        return baseMapper.insert(logisticsCompanyEntity);
    }

    /**
     * 修改物流公司
     * @param logisticsCompanyEntity
     * @return
     */
    @Override
    public int updateLogisticsCompany(LogisticsCompanyEntity logisticsCompanyEntity) {
        //先判断是否有重复的
        LogisticsCompanyEntity logisticsCompany = this.getLogisticsCompanyByParams(new MapUtil()
                .put("name",logisticsCompanyEntity.getName())
                .put("code", logisticsCompanyEntity.getCode())
                .put("id", logisticsCompanyEntity.getId()));
        if (logisticsCompany != null) {
            return -1;
        }
        logisticsCompanyEntity.setUpdateTime(new Date());
        return baseMapper.updateById(logisticsCompanyEntity);
    }

    /**
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 物流公司名称
     * @param abbreviation 物流公司简称
     * @param code 物流公司编码
     * @param adminUserId 所属商家id
     * @return
     */
    @Override
    public PageUtil getWithExpressSettingByPage(Integer pageNum,
                                                Integer pageSize,
                                                String name,
                                                String abbreviation,
                                                String code,
                                                Long adminUserId) {
        Map<String, Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        Page<LogisticsCompanyEntity> page = (Page<LogisticsCompanyEntity>)new PageBuilder<LogisticsCompanyEntity>()
                .getPage(pageParams);
        QueryWrapper<LogisticsCompanyEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name)
                .like(StringUtils.isNotBlank(abbreviation), "abbreviation", abbreviation)
                .like(StringUtils.isNotBlank(code), "code", code)
                .orderByDesc("enable") //开启的放在前面
                .orderByAsc("order_num");
        IPage<LogisticsCompanyEntity> iPage = baseMapper.getWithExpressSettingByPage(page, wrapper, adminUserId);
        return new PageUtil(iPage);
    }
}
