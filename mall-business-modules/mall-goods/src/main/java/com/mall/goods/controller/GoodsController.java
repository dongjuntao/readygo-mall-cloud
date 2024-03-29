package com.mall.goods.controller;

import com.mall.admin.api.feign.FeignAdminUserService;
import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.base.utils.PageUtil;
import com.mall.goods.entity.*;
import com.mall.goods.enums.GoodsStatusEnum;
import com.mall.goods.service.GoodsService;
import com.mall.goods.service.GoodsSkuService;
import com.mall.goods.vo.GoodsCountByCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author DongJunTao
 * @Description 商品管理
 * @Date 2021/6/25 16:27
 * @Version 1.0
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private FeignAdminUserService feignAdminUserService;

    /**
     * 新增商品
     * @param goodsEntity
     * @return
     */
    @PostMapping("save")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult save(@RequestBody GoodsEntity goodsEntity) {
        //保存商品
        goodsEntity.setCreateTime(new Date());
        //新创建状态
        goodsEntity.setGoodsStatus(GoodsStatusEnum.NEW_CREATED);
        Integer totalStock = 0;//总库存
        List<GoodsSkuEntity> goodsSkuList = goodsEntity.getGoodsSkuList();
        totalStock = goodsSkuList.stream().mapToInt(sku->sku.getStock()).sum();
        goodsEntity.setTotalStock(totalStock);
        goodsEntity.setTotalSales(0);
        goodsService.saveOrUpdate(goodsEntity);
        for (GoodsSkuEntity goodsSku : goodsSkuList) {
            goodsSku.setGoodsId(goodsEntity.getId());//设置sku的商品id
        }
        //新增商品sku
        goodsSkuService.saveBatch(goodsSkuList);
        return CommonResult.success();
    }

    /**
     * 修改商品
     * @param goodsEntity
     * @return
     */
    @PutMapping("update")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(@RequestBody GoodsEntity goodsEntity) {
        List<GoodsSkuEntity> skuList = goodsEntity.getGoodsSkuList();
        if (skuList.get(0).getId() != null) { //商品规格未变，即未重新选择规格
            //直接更新sku列表
            goodsSkuService.updateBatchById(skuList);
        }else {
            GoodsEntity old = goodsService.getGoodsAndSku(goodsEntity.getId());
            List<GoodsSkuEntity> oldSkuList = old.getGoodsSkuList();
            List<Long> skuIdList = new ArrayList<>();
            if (oldSkuList != null && oldSkuList.size()>0) {
                oldSkuList.forEach(sku->{ skuIdList.add(sku.getId()); });
                //删除旧的商品sku信息
                goodsSkuService.deleteBatch(skuIdList);
            }
            skuList.stream().forEach(sku->{sku.setGoodsId(goodsEntity.getId());});
            //保存商品sku
            goodsSkuService.saveBatch(skuList);
        }
        Integer totalStock = skuList.stream().mapToInt(sku->sku.getStock()).sum();
        //保存商品
        goodsEntity.setUpdateTime(new Date());
        goodsEntity.setTotalStock(totalStock);//总库存
        goodsService.saveOrUpdate(goodsEntity);
        return CommonResult.success();
    }

    /**
     * 商品信息
     */
    @GetMapping("getGoodsById")
    public CommonResult info(@RequestParam("id") Long id){
        GoodsEntity goodsEntity = goodsService.getGoodsAndSku(id);
        List<GoodsSkuEntity> skuList = goodsEntity.getGoodsSkuList();
        Optional<GoodsSkuEntity> sku =
                skuList.stream().filter(Objects::nonNull).min(Comparator.comparing(GoodsSkuEntity::getSellingPrice));//最小值
        if (sku.isPresent()) {
            goodsEntity.setMinPrice(sku.get().getSellingPrice());
        }
        return CommonResult.success(goodsEntity);
    }

    /**
     * 商品列表（分页）
     */
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                             @RequestParam(value = "pageSize",required = false) Integer pageSize,
                             @RequestParam(value = "name",required = false) String name,
                             @RequestParam(value = "adminUserId",required = false) Long adminUserId,
                             @RequestParam(value = "categoryIds",required = false) String categoryIds,
                             @RequestParam(value = "goodsStatus",required = false) String goodsStatus){
        PageUtil pageResult = goodsService.queryPage(pageNum,pageSize,name,adminUserId,categoryIds, goodsStatus);
        //根据分页结果查询商品信息，并设置属性
        List list = pageResult.getList();
        if (list.size() == 0) {
            return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
        }
        Long[] adminUserIds = new Long[list.size()];
        for (int i=0; i<list.size(); i++) {
            GoodsEntity goods = (GoodsEntity) list.get(i);
            adminUserIds[i] = goods.getAdminUserId();
        }
        //远程调用admin服务，获取商户信息
        CommonResult result = feignAdminUserService.listByIds(adminUserIds);
        if (result != null && "200".equals(result.getCode())) {
            List resultList = (List) result.getData();
            for (int i=0;i<list.size();i++) {
                for(int j=0; j<resultList.size(); j++) {
                    Map<String,Object> map = (Map)resultList.get(j);
                    if (((GoodsEntity)list.get(i)).getAdminUserId().toString().equals(map.get("id").toString())) {
                        ((GoodsEntity) list.get(i)).setMerchantName(map.get("name").toString());
                        break;//一旦找到，退出当前循环，减少循环次数
                    }
                }
            }
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), pageResult);
    }

    /**
     * 商品列表（不分页）
     */
    @GetMapping("/listAll")
    public CommonResult listAll(@RequestParam(value = "adminUserId",required = false) Long adminUserId){
        List<GoodsEntity> allGoodsEntityList = goodsService.getAllGoodsList(adminUserId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), allGoodsEntityList);
    }

    /**
     * 根据ids集合所有商品列表（不分页）
     */
    @GetMapping("/listByIds")
    public CommonResult listByIds(@RequestParam Long[] ids){
        List<GoodsEntity> goodsEntityList = goodsService.getByIds(ids);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsEntityList);
    }

    /**
     * 删除商品
     */
    @DeleteMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delete(@RequestBody Long[] ids){
        for (Long id : ids) {
            //删除关联的商品规格详细信息
            Map<String, Object> map = new HashMap<>();
            map.put("goods_id", id);
            goodsSkuService.removeByMap(map);
            //删除商品规格
            goodsService.removeById(id);
        }
        return CommonResult.success();
    }

    /**
     * 申请上架
     */
    @PutMapping("applyOnSale")
    public CommonResult applyOnSale(@RequestParam("goodsId") Long goodsId){
        return goodsService.applyOnSale(goodsId) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 上架审核
     */
    @PutMapping("audit")
    public CommonResult audit(@RequestParam("goodsId") Long goodsId, boolean isAudit){
        return goodsService.audit(goodsId, isAudit) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 下架
     */
    @PutMapping("offShelf")
    public CommonResult offShelf(@RequestParam("goodsId") Long goodsId){
        return goodsService.offShelf(goodsId) > 0 ? CommonResult.success() : CommonResult.fail();
    }

    /**
     * 商品列表（所有商品，包括详细信息）
     */
    @GetMapping("/allGoodsWithDetail")
    public CommonResult allGoodsWithDetail(){
        List<GoodsEntity> allGoodsEntityList = goodsService.getAllGoodsWithDetail();
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), allGoodsEntityList);
    }

    /**
     * 商品数量统计
     * @return
     */
    @GetMapping("count")
    public CommonResult count(@RequestParam(value = "goodsStatus", required = false) String goodsStatus,
                              @RequestParam(value = "adminUserId", required = false) Long adminUserId) {
        int count = goodsService.count(goodsStatus, adminUserId);
        return CommonResult.success(count);
    }

    /**
     * 商品数量统计
     * @return
     */
    @GetMapping("getGoodsCountByCategory")
    public CommonResult getGoodsCountByCategory(@RequestParam(value = "adminUserId",required = false) Long adminUserId) {
        List<GoodsCountByCategoryVO> goodsCountByCategory = goodsService.getGoodsCountByCategory(adminUserId);
        return CommonResult.success(goodsCountByCategory);
    }


}
