package com.mall.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.admin.api.feign.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.DateUtil;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.api.FeignGoodsService;
import com.mall.seckill.entity.SeckillConfigEntity;
import com.mall.seckill.entity.SeckillGoodsSkuEntity;
import com.mall.seckill.service.SeckillConfigService;
import com.mall.seckill.service.SeckillGoodsSkuService;
import com.mall.seckill.vo.GoodsSkuVO;
import com.mall.seckill.vo.SeckillGoodsSkuVO;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    @Autowired
    private FeignGoodsService feignGoodsService;

    @Autowired
    private SeckillGoodsSkuService seckillGoodsSkuService;

    /**
     * 秒杀配置列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam Map<String, Object> params){
        PageUtil page = seckillConfigService.getByPage(params);

        //根据分页结果查询商家信息，并设置属性【商家名称】
        List list = page.getList();
        if (list.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), page);
        }
        Long[] adminUserIds = new Long[list.size()];
        Long[] goodsIds = new Long[list.size()];
        for (int i=0; i<list.size(); i++) {
            SeckillConfigEntity seckillConfig = (SeckillConfigEntity) list.get(i);
            adminUserIds[i] = seckillConfig.getAdminUserId();
            goodsIds[i] = seckillConfig.getGoodsId();
        }
        //远程调用admin服务，获取商户信息
        CommonResult adminResult = feignAdminUserService.listByIds(adminUserIds);
        if (adminResult != null && "200".equals(adminResult.getCode())) {
            List resultList = (List) adminResult.getData();
            for (int i=0;i<list.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if (((SeckillConfigEntity)list.get(i)).getAdminUserId().toString().equals(map.get("id").toString())) {
                        ((SeckillConfigEntity) list.get(i)).setMerchantName(map.get("name").toString());
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }

        //远程调用goods服务，获取商品信息
        CommonResult goodsResult = feignGoodsService.listByIds(goodsIds);
        if (goodsResult != null && "200".equals(goodsResult.getCode())) {
            List resultList = (List) goodsResult.getData();
            for (int i=0;i<list.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if (((SeckillConfigEntity)list.get(i)).getGoodsId().toString().equals(map.get("id").toString())) {
                        ((SeckillConfigEntity) list.get(i)).setGoodsName(map.get("name").toString());
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }

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
     * 查询单个秒杀配置
     */
    @GetMapping("getById")
    public CommonResult getById(@RequestParam("seckillConfigId") Long seckillConfigId){
        SeckillConfigEntity seckillConfigEntity = seckillConfigService.getById(seckillConfigId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), seckillConfigEntity);
    }

    /**
     * 查询单个秒杀配置（详细）包括【关联的商品详细信息，商户名称，商品名称等】
     */
    @GetMapping("getSeckillConfigById")
    public CommonResult getSeckillConfigById(@RequestParam("seckillConfigId") Long seckillConfigId){
        SeckillConfigEntity seckillConfigEntity = seckillConfigService.getById(seckillConfigId);
        //远程获取用户信息，作为商户名称
        CommonResult userResult = feignAdminUserService.getAdminUserById(seckillConfigEntity.getAdminUserId());
        if (userResult != null && "200".equals(userResult.getCode())) {
            JSONObject userJSON = JSONObject.parseObject(JSON.toJSONString(userResult.getData()));
            seckillConfigEntity.setMerchantName(userJSON.getString("name"));
        }
        //远程获取商信息，作为商品名称
        CommonResult goodsResult = feignGoodsService.getGoodsById(seckillConfigEntity.getGoodsId());
        if (goodsResult != null && "200".equals(goodsResult.getCode())) {
            JSONObject goodsJSON = JSONObject.parseObject(JSON.toJSONString(goodsResult.getData()));
            seckillConfigEntity.setGoodsName(goodsJSON.getString("name"));
            List<GoodsSkuVO> goodsSkuList = goodsJSON.getJSONArray("goodsSkuList").toJavaList(GoodsSkuVO.class);
            List<SeckillGoodsSkuEntity> seckillGoodsSkuList = seckillConfigEntity.getSeckillGoodsSkuList();
            if (!CollectionUtils.isEmpty(seckillGoodsSkuList)) {
                for (GoodsSkuVO goodsSkuVO : goodsSkuList) {
                    for (SeckillGoodsSkuEntity seckillGoodsSkuEntity: seckillGoodsSkuList) {
                        if (goodsSkuVO.getId().equals(seckillGoodsSkuEntity.getGoodsSkuId())) {
                            SeckillGoodsSkuVO seckillGoodsSku = new SeckillGoodsSkuVO();
                            BeanUtils.copyProperties(seckillGoodsSkuEntity, seckillGoodsSku);
                            goodsSkuVO.setSeckillGoodsSkuVO(seckillGoodsSku);
                        }
                    }
                }
            }
            seckillConfigEntity.setGoodsSkuList(goodsSkuList);
        }
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

    /**
     * 根据商品id查询是否是秒杀中，且返回秒杀相关配置信息
     */
    @GetMapping("getSeckillConfigByParams")
    public CommonResult getSeckillConfigByParams(@RequestParam(value = "dateTime",required = false) String dateTime,
                                                 @RequestParam("goodsId") Long goodsId){
        LocalDateTime localDateTime;
        if (StringUtils.isEmpty(dateTime)) {
            localDateTime = LocalDateTime.now();//当前时间
        }else {
            localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        String currentBatch = seckillGoodsSkuService.getBatchByCurrentTime(localDateTime);
        String startDateTime = currentBatch.split(",")[0]; //起始日期时间
        String endDateTime = currentBatch.split(",")[1]; //结束日期时间
        Map<String, Object> params = new HashMap<>();
        params.put("seckillDate", startDateTime.split(" ")[0]);
        params.put("seckillStartTime", startDateTime.split(" ")[1]);
        params.put("seckillEndTime", endDateTime.split(" ")[1]);
        params.put("goodsId", goodsId);
        return CommonResult.success(seckillConfigService.getSeckillConfigByParams(params));
    }
}
