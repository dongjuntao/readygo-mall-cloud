package com.mall.seckill.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.DateUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.seckill.entity.SeckillConfigEntity;
import com.mall.seckill.service.SeckillConfigService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 秒杀配置controller
 * @Date 2022/1/6 10:09
 * @Version 1.0
 */
@RestController
@RequestMapping("seckillConfig")
public class SeckillConfigController {

    @Autowired
    private SeckillConfigService seckillConfigService;

    /**
     * 秒杀配置列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = seckillConfigService.getByPage(params);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
    }

    /**
     * 保存秒杀配置
     * @param seckillConfigEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult saveSeckillConfig(@RequestBody SeckillConfigEntity seckillConfigEntity) throws ParseException {
        seckillConfigEntity.setAuthStatus(0);
        seckillConfigEntity.setCreateTime(new Date());
        String startAndEndTime = seckillConfigEntity.getStartAndEndTime();
        if (!StringUtil.isNullOrEmpty(startAndEndTime)) {
            seckillConfigEntity.setSeckillStartTime(DateUtil.getDateByDateString(startAndEndTime.split(" - ")[0],"HH:mm:ss"));
            seckillConfigEntity.setSeckillEndTime(DateUtil.getDateByDateString(startAndEndTime.split(" - ")[1],"HH:mm:ss"));
        }
        return seckillConfigService.saveSeckillConfig(seckillConfigEntity) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 修改秒杀配置
     * @param seckillConfigEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult updateSeckillConfig(@RequestBody SeckillConfigEntity seckillConfigEntity) throws ParseException {
        seckillConfigEntity.setUpdateTime(new Date());
        String startAndEndTime = seckillConfigEntity.getStartAndEndTime();
        if (!StringUtil.isNullOrEmpty(startAndEndTime)) {
            seckillConfigEntity.setSeckillStartTime(DateUtil.getDateByDateString(startAndEndTime.split(" - ")[0],"HH:mm:ss"));
            seckillConfigEntity.setSeckillEndTime(DateUtil.getDateByDateString(startAndEndTime.split(" - ")[1],"HH:mm:ss"));
        }
        return seckillConfigService.updateSeckillConfig(seckillConfigEntity) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 所有单个秒杀配置
     */
    @GetMapping("getSeckillConfigById")
    public CommonResult getSeckillConfigById(@RequestParam("seckillConfigId") Long seckillConfigId){
        SeckillConfigEntity seckillConfigEntity = seckillConfigService.getById(seckillConfigId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), seckillConfigEntity);
    }

    /**
     * 删除秒杀配置
     */
    @DeleteMapping("delete")
    public CommonResult delete(@RequestBody Long[] seckillConfigIds){
        seckillConfigService.deleteBatch(seckillConfigIds);
        return CommonResult.success();
    }

    /**
     * 修改秒杀配置状态
     * @param seckillConfigId 秒杀配置id
     * @param status 状态
     * @return
     */
    @PutMapping("updateStatus")
    public CommonResult updateStatus(@RequestParam("seckillConfigId") Long seckillConfigId,
                                     @RequestParam("status") Boolean status) {
        return seckillConfigService.updateStatus(seckillConfigId, status) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 秒杀配置审核
     * @param seckillConfigId 秒杀配置id
     * @param authStatus 审核状态【1:通过 2：拒绝】
     * @param authOpinion 审核意见【如果拒绝，请必填】
     * @return
     */
    @PutMapping("auth")
    public CommonResult auth(@RequestParam("seckillConfigId") Long seckillConfigId,
                             @RequestParam("authStatus") Integer authStatus,
                             @RequestParam("authOpinion") String authOpinion) {
        return seckillConfigService.auth(seckillConfigId, authStatus, authOpinion) > 0 ? CommonResult.success() : CommonResult.fail();
    }
}
