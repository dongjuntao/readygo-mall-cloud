package com.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.util.PageUtil;
import com.mall.seckill.entity.SeckillConfigEntity;

import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀服务service
 * @Date 2022/1/6 9:44
 * @Version 1.0
 */
public interface SeckillConfigService extends IService<SeckillConfigEntity> {

    /**
     * 保存秒杀配置
     * @param seckillConfigEntity
     * @return
     */
    int saveSeckillConfig(SeckillConfigEntity seckillConfigEntity);

    /**
     * 更新秒杀配置
     * @param seckillConfigEntity
     * @return
     */
    int updateSeckillConfig(SeckillConfigEntity seckillConfigEntity);

    /**
     * 分页查询秒杀配置列表
     * @param params
     * @return
     */
    PageUtil getByPage(Map<String, Object> params);

    /**
     * 根据id查询秒杀配置
     * @param seckillConfigId
     * @return
     */
    SeckillConfigEntity getById(Long seckillConfigId);

    /**
     * 删除秒杀配置
     * @param seckillConfigIds
     */
    void deleteBatch(Long[] seckillConfigIds);

    /**
     * 修改秒杀配置状态
     * @param seckillConfigId 秒杀配置id
     * @param status 状态
     * @return
     */
    int updateStatus(Long seckillConfigId, Boolean status);

    /**
     * 秒杀配置审核
     * @param seckillConfigId
     * @param authStatus
     * @param authOpinion
     * @return
     */
    int auth(Long seckillConfigId, Integer authStatus, String authOpinion);
}
