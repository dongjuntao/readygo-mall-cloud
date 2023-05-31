package com.mall.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.common.base.utils.PageUtil;
import com.mall.seckill.entity.SeckillConfigEntity;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
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
     * @return
     */
    PageUtil getByPage(Integer pageNum, Integer pageSize, String name, Long adminUserId, Integer authStatus);

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

    /**
     * 通过参数查询秒杀配置列表
     * @param params
     * @return
     */
    List<SeckillConfigEntity> getByParams(Map<String, Object> params);

    SeckillConfigEntity getSeckillConfigByParams(Map<String, Object> params);

    String getBatchByCurrentTime(LocalDateTime localDateTime);

}
