package com.mall.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.base.utils.DateUtil;
import com.mall.common.base.utils.MapUtil;
import com.mall.common.base.utils.PageBuilder;
import com.mall.common.base.utils.PageUtil;
import com.mall.seckill.entity.SeckillConfigEntity;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.mapper.SeckillConfigMapper;
import com.mall.seckill.mapper.SeckillGoodsSkuMapper;
import com.mall.seckill.service.SeckillConfigService;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private SeckillGoodsSkuMapper seckillGoodsSkuMapper;

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
    public PageUtil getByPage(Integer pageNum, Integer pageSize, String name, Long adminUserId, Integer authStatus) {
        Map<String,Object> pageParams = new MapUtil().put("pageNum",pageNum).put("pageSize",pageSize);
        Page<SeckillConfigEntity> page =
                (Page<SeckillConfigEntity>)new PageBuilder<SeckillConfigEntity>().getPage(pageParams);
        QueryWrapper<SeckillConfigEntity> wrapper = new QueryWrapper<>();
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
        //删除秒杀配置
        this.removeByIds(Arrays.asList(seckillConfigIds));
        //删除秒杀商品
        QueryWrapper<SeckillGoodsSkuEntity> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.in("seckill_config_id", seckillConfigIds);
        seckillGoodsSkuMapper.delete(deleteWrapper);
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

    /**
     * 通过参数查询秒杀配置列表
     * @param params
     * @return
     */
    @Override
    public List<SeckillConfigEntity> getByParams(Map<String, Object> params) {
        return baseMapper.getByParams(params);
    }

    @Override
    public SeckillConfigEntity getSeckillConfigByParams(Map<String, Object> params) {
        return baseMapper.getSeckillConfigByParams(params);
    }

    @Override
    public String getBatchByCurrentTime(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour(); //当前小时
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateTimeFormatter.format(localDateTime);
        String currentBatch = "";
        switch (hour) {
            case 0:
            case 1:
                currentBatch = date+" 00:00:00" + "," +date+" 02:00:00"; break;
            case 2:
            case 3:
                currentBatch = date+" 02:00:00" + "," +date+" 04:00:00"; break;
            case 4:
            case 5:
                currentBatch = date+" 04:00:00" + "," +date+" 06:00:00"; break;
            case 6:
            case 7:
                currentBatch = date+" 06:00:00" + "," +date+" 08:00:00"; break;
            case 8:
            case 9:
                currentBatch = date+" 08:00:00" + "," +date+" 10:00:00"; break;
            case 10:
            case 11:
                currentBatch = date+" 10:00:00" + "," +date+" 12:00:00"; break;
            case 12:
            case 13:
                currentBatch = date+" 12:00:00" + "," +date+" 14:00:00"; break;
            case 14:
            case 15:
                currentBatch = date+" 14:00:00" + "," +date+" 16:00:00"; break;
            case 16:
            case 17:
                currentBatch = date+" 16:00:00" + "," +date+" 18:00:00"; break;
            case 18:
            case 19:
                currentBatch = date+" 18:00:00" + "," +date+" 20:00:00"; break;
            case 20:
            case 21:
                currentBatch = date+" 20:00:00" + "," +date+" 22:00:00"; break;
            case 22:
            case 23:
                currentBatch = date+" 22:00:00" + "," +date+" 23:59:59"; break;
            default: currentBatch = null;
        }
        return currentBatch;
    }
}
