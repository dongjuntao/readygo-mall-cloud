package com.mall.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.admin.entity.HomepagePlateEntity;
import com.mall.admin.entity.PayTypeEntity;
import com.mall.admin.mapper.PayTypeMapper;
import com.mall.admin.service.PayTypeService;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 支付管理（支付方式）
 * @Date 2022/6/19 14:45
 * @Version 1.0
 */
@Service
public class PayTypeServiceImpl extends ServiceImpl<PayTypeMapper, PayTypeEntity> implements PayTypeService {

    @Autowired
    private PayTypeMapper payTypeMapper;

    /**
     * 分页查询支付方式
     * @param pageNum 页码
     * @param enable 每页数量
     * @param name 支付方式名称
     * @param enable 是否启用
     * @return
     */
    @Override
    public PageUtil queryPage(Integer pageNum, Integer pageSize, String name, Boolean enable) {
        Map<String, Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        IPage<PayTypeEntity> page = this.page(
                new PageBuilder<PayTypeEntity>().getPage(pageParams),
                new QueryWrapper<PayTypeEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .eq(enable != null, "enable", enable)
        );
        return new PageUtil(page);
    }

    /**
     * 查询所有支付方式（根据指定条件）
     * @param name 支付方式名称
     * @param enable 是否启用
     * @return
     */
    @Override
    public List<PayTypeEntity> getPayTypeList(String name, Boolean enable) {
        QueryWrapper<PayTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(enable != null, "enable", enable);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        return payTypeMapper.selectList(queryWrapper);
    }

    /**
     * 根据id获取支付方式信息
     * @param id
     * @return
     */
    @Override
    public PayTypeEntity getPayTypeById(Long id) {
        return payTypeMapper.selectById(id);
    }

    /**
     * 保存支付方式信息
     * @param payType
     * @return
     */
    @Override
    public int savePayType(PayTypeEntity payType) {
        payType.setCreateTime(new Date());
        return payTypeMapper.insert(payType);
    }

    /**
     * 修改支付方式信息
     * @param payType
     * @return
     */
    @Override
    public int updatePayType(PayTypeEntity payType) {
        return payTypeMapper.updateById(payType);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        payTypeMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateEnable(Long id, Boolean enable) {
        PayTypeEntity payType = this.getPayTypeById(id);
        payType.setEnable(enable);
        return payTypeMapper.updateById(payType);
    }
}
