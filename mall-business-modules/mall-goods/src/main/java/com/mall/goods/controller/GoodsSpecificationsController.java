package com.mall.goods.controller;

import com.mall.admin.api.feign.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.GoodsSpecificationsDetailEntity;
import com.mall.goods.entity.GoodsSpecificationsEntity;
import com.mall.goods.service.GoodsSpecificationsDetailService;
import com.mall.goods.service.GoodsSpecificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description 商品规格管理
 * @Date 2021/6/25 16:30
 * @Version 1.0
 */
@RestController
@RequestMapping("goods/specifications")
public class GoodsSpecificationsController {

    @Autowired
    private GoodsSpecificationsService goodsSpecificationsService;

    @Autowired
    private GoodsSpecificationsDetailService goodsSpecificationsDetailService;

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    /**
     * 新增商品规格
     * @param goodsSpecificationsEntity
     * @return
     */
    @PostMapping("save")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult save(@RequestBody GoodsSpecificationsEntity goodsSpecificationsEntity) {
        goodsSpecificationsEntity.setCreateTime(new Date());
        //保存商品规格
        goodsSpecificationsService.saveOrUpdate(goodsSpecificationsEntity);
        //商品规格详细表中存储商品规格id
        List<GoodsSpecificationsDetailEntity> detailList = goodsSpecificationsEntity.getGoodsSpecificationsDetailEntityList();
        if (detailList != null && detailList.size()>0) {
            detailList.forEach(detail->{detail.setGoodsSpecificationsId(goodsSpecificationsEntity.getId());});
        }
        //保存商品规格详细信息
        goodsSpecificationsDetailService.saveBatch(detailList);
        return CommonResult.success();
    }

    /**
     * 修改商品规格
     * @param goodsSpecificationsEntity
     * @return
     */
    @PutMapping("update")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(@RequestBody GoodsSpecificationsEntity goodsSpecificationsEntity) {
        goodsSpecificationsEntity.setUpdateTime(new Date());
        //查找原来的商品规格详细信息
        GoodsSpecificationsEntity old = goodsSpecificationsService.getGoodsSpecificationsAndDetail(goodsSpecificationsEntity.getId());
        List<GoodsSpecificationsDetailEntity> oldDetailList = old.getGoodsSpecificationsDetailEntityList();
        List<Long> detailIdList = new ArrayList<>();
        if (oldDetailList != null && oldDetailList.size()>0) {
            oldDetailList.forEach(detail->{ detailIdList.add(detail.getId()); });
            //删除旧的商品规格详细信息
            goodsSpecificationsDetailService.deleteBatch(detailIdList);
        }
        //保存商品规格
        goodsSpecificationsService.saveOrUpdate(goodsSpecificationsEntity);
        //商品规格详细表中存储商品规格id
        List<GoodsSpecificationsDetailEntity> detailList = goodsSpecificationsEntity.getGoodsSpecificationsDetailEntityList();
        if (detailList != null && detailList.size()>0) {
            detailList.forEach(detail->{detail.setGoodsSpecificationsId(goodsSpecificationsEntity.getId());});
        }
        //保存商品规格详细信息
        goodsSpecificationsDetailService.saveBatch(detailList);
        return CommonResult.success();
    }

    /**
     * 规格信息
     */
    @GetMapping("getGoodsSpecificationsById")
    public CommonResult info(@RequestParam("id") Long id){
        GoodsSpecificationsEntity goodsSpecificationsEntity = goodsSpecificationsService.getGoodsSpecificationsAndDetail(id);
        return CommonResult.success(goodsSpecificationsEntity);
    }

    /**
     * 规格列表
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "adminUserId", required = false) Long adminUserId) {
        PageUtil pageResult = goodsSpecificationsService.queryPage(pageNum, pageSize,name, adminUserId);
        //根据分页结果查询商品信息，并设置属性
        List list = pageResult.getList();
        if (list.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
        }
        Long[] adminUserIds = new Long[list.size()];
        for (int i=0; i<list.size(); i++) {
            GoodsSpecificationsEntity goodsSpecifications = (GoodsSpecificationsEntity) list.get(i);
            adminUserIds[i] = goodsSpecifications.getAdminUserId();
        }
        //远程调用admin服务，获取商户信息
        CommonResult result = feignAdminUserService.listByIds(adminUserIds);
        if (result != null && "200".equals(result.getCode())) {
            List resultList = (List) result.getData();
            for (int i=0;i<list.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if (((GoodsSpecificationsEntity)list.get(i)).getAdminUserId().toString().equals(map.get("id").toString())) {
                        ((GoodsSpecificationsEntity) list.get(i)).setMerchantName(map.get("name").toString());
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }

        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
    }

    /**
     * 品牌列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(){
        List<GoodsSpecificationsEntity> goodsSpecificationsEntityList = goodsSpecificationsService.getGoodsSpecificationList();
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsSpecificationsEntityList);
    }

    /**
     * 删除规格
     */
    @DeleteMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delete(@RequestBody Long[] ids){
        for (Long id : ids) {
            //删除关联的商品规格详细信息
            Map<String, Object> map = new HashMap<>();
            map.put("goods_specifications_id", id);
            goodsSpecificationsDetailService.removeByMap(map);
            //删除商品规格
            goodsSpecificationsService.removeById(id);
        }
        return CommonResult.success();
    }
}
