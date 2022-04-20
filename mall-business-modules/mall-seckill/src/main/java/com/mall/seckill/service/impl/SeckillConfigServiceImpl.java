package com.mall.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.seckill.entity.SeckillConfigEntity;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.mapper.SeckillConfigMapper;
import com.mall.seckill.service.SeckillConfigService;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀服务service实现
 * @Date 2022/1/6 9:46
 * @Version 1.0
 */
@Service("seckillConfigService")
public class SeckillConfigServiceImpl
        extends ServiceImpl<SeckillConfigMapper, SeckillConfigEntity> implements SeckillConfigService {

    @Autowired
    private SeckillGoodsSkuService seckillGoodsSkuService;

    /**
     * 新增秒杀配置
     * @param seckillConfigEntity
     * @return
     */
    @Override
    @Transactional
    public int saveSeckillConfig(SeckillConfigEntity seckillConfigEntity) {
        //先新增秒杀配置
        int count = baseMapper.insert(seckillConfigEntity);
        //在新增秒杀商品详细信息
        if (count>0) {
            if (seckillConfigEntity != null && !CollectionUtils.isEmpty(seckillConfigEntity.getGoodsSkuList())) {
                List<SeckillGoodsSkuEntity> seckillGoodsSkuEntityList = new ArrayList<>();
                for (GoodsSkuVO goodsSkuVO : seckillConfigEntity.getGoodsSkuList()) {
                    SeckillGoodsSkuEntity seckillGoodsSkuEntity = new SeckillGoodsSkuEntity();
                    //只保存秒杀商品详细信息【其他信息在goods_sku表，不保存在seckill_goods_sku表】
                    goodsSkuVO.getSeckillGoodsSkuVO().setSeckillConfigId(seckillConfigEntity.getId());
                    BeanUtils.copyProperties(goodsSkuVO.getSeckillGoodsSkuVO(), seckillGoodsSkuEntity);
                    seckillGoodsSkuEntityList.add(seckillGoodsSkuEntity);
                }
                seckillGoodsSkuService.saveBatch(seckillGoodsSkuEntityList);
            }
        }
        return count;
    }

    /**
     * 修改秒杀配置
     * @param seckillConfigEntity
     * @return
     */
    @Override
    @Transactional
    public int updateSeckillConfig(SeckillConfigEntity seckillConfigEntity) {
        //先删除原先的秒杀商品详细信息
        Long seckillConfigId = seckillConfigEntity.getId();
        QueryWrapper<SeckillGoodsSkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seckill_config_id", seckillConfigId);
        seckillGoodsSkuService.remove(queryWrapper);
        //新增秒杀商品详细信息
        if (seckillConfigEntity != null && !CollectionUtils.isEmpty(seckillConfigEntity.getGoodsSkuList())) {
            List<SeckillGoodsSkuEntity> seckillGoodsSkuEntityList = new ArrayList<>();
            for (GoodsSkuVO goodsSkuVO : seckillConfigEntity.getGoodsSkuList()) {
                SeckillGoodsSkuEntity seckillGoodsSkuEntity = new SeckillGoodsSkuEntity();
                goodsSkuVO.getSeckillGoodsSkuVO().setSeckillConfigId(seckillConfigEntity.getId());
                BeanUtils.copyProperties(goodsSkuVO.getSeckillGoodsSkuVO(), seckillGoodsSkuEntity);
                seckillGoodsSkuEntityList.add(seckillGoodsSkuEntity);
            }
            seckillGoodsSkuService.saveBatch(seckillGoodsSkuEntityList);
        }
        return baseMapper.updateById(seckillConfigEntity);
    }

    /**
     * 分页查询秒杀配置列表
     * @param params
     * @return
     */
    @Override
    public PageUtil getByPage(Map<String, Object> params) {
        Page<SeckillConfigEntity> page = (Page<SeckillConfigEntity>)new PageBuilder<SeckillConfigEntity>().getPage(params);
        QueryWrapper<SeckillConfigEntity> wrapper = new QueryWrapper<>();
        String name = String.valueOf(params.get("name"));//秒杀商品名称
        Long adminUserId = params.get("adminUserId") == null ? null: Long.valueOf((params.get("adminUserId").toString()));
        Integer authStatus = params.get("authStatus") == null ? null : Integer.valueOf((params.get("authStatus").toString()));
        wrapper
                .like(StringUtils.isNotBlank(name), "c.name", name)
                .eq(adminUserId != null, "admin_user_id", adminUserId)
                .eq(authStatus != null, "auth_status", authStatus);
        IPage<SeckillConfigEntity> iPage = baseMapper.queryPage(page, wrapper);
        return new PageUtil(iPage);
    }

    /**
     * 删除秒杀配置（支持批量删除）
     * @param seckillConfigIds
     */
    @Override
    public void deleteBatch(Long[] seckillConfigIds) {
        this.removeByIds(Arrays.asList(seckillConfigIds));
    }

    @Override
    public int updateStatus(Long seckillConfigId, Boolean status) {
        SeckillConfigEntity seckillConfigEntity = this.getById(seckillConfigId);
        if (seckillConfigEntity == null) {
            return -1;
        }
        seckillConfigEntity.setStatus(status);
        return baseMapper.updateById(seckillConfigEntity);
    }

    @Override
    public SeckillConfigEntity getById(Long seckillConfigId) {
        return baseMapper.getById(seckillConfigId);
    }

    /**
     * 秒杀配置审核
     * @param seckillConfigId
     * @param authStatus
     * @param authOpinion
     * @return
     */
    @Override
    public int auth(Long seckillConfigId, Integer authStatus, String authOpinion) {
        SeckillConfigEntity seckillConfigEntity = this.getById(seckillConfigId);
        if (seckillConfigEntity == null) {
            return -1;
        }
        seckillConfigEntity.setAuthStatus(authStatus);
        seckillConfigEntity.setAuthOpinion(authOpinion);
        return baseMapper.updateById(seckillConfigEntity);
    }
}
