package com.mall.goods.controller;

import com.mall.common.base.CommonResult;
import com.mall.common.base.enums.ResultCodeEnum;
import com.mall.common.redis.util.RedisUtil;
import com.mall.goods.constants.RedisKeyConstant;
import com.mall.goods.entity.GoodsCategoryEntity;
import com.mall.goods.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DongJunTao
 * @Description 商品分类管理
 * @Date 2021/6/25 16:27
 * @Version 1.0
 */
@RestController
@RequestMapping("goods/category")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取商品分类树
     * @return
     */
    @GetMapping("/getGoodsCategoryTree")
    public CommonResult getGoodsCategoryTree() {
        List<GoodsCategoryEntity> goodsCategoryTree = (List<GoodsCategoryEntity>)
                redisUtil.hGet(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY);
        if (goodsCategoryTree == null) {
            goodsCategoryTree = goodsCategoryService.queryGoodsCategoryTree(0L);
            redisUtil.hSet(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY, goodsCategoryTree);
        }
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsCategoryTree);
    }

    /**
     * 新增，修改商品分类时选择上级分类
     * @return
     */
    @GetMapping("/select")
    public CommonResult select() {
        GoodsCategoryEntity categoryEntity = new GoodsCategoryEntity();
        categoryEntity.setId(0L);
        categoryEntity.setName("一级分类");
        categoryEntity.setParentId(-1L);
        List<GoodsCategoryEntity> goodsCategoryTree = (List<GoodsCategoryEntity>)
                redisUtil.hGet(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY);
        if (goodsCategoryTree == null){
            goodsCategoryTree  = goodsCategoryService.queryGoodsCategoryTree(0L);
            redisUtil.hSet(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY, goodsCategoryTree);
        }
        goodsCategoryTree.add(categoryEntity);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsCategoryTree);
    }

    /**
     * 新增商品分类
     * @param goodsCategoryEntity
     * @return
     */
    @PostMapping("save")
    public CommonResult save(@RequestBody GoodsCategoryEntity goodsCategoryEntity) {
        goodsCategoryEntity.setCreateTime(new Date());
        goodsCategoryService.saveOrUpdate(goodsCategoryEntity);
        redisUtil.hDel(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY);
        return CommonResult.success();
    }

    /**
     * 修改商品分类
     * @param goodsCategoryEntity
     * @return
     */
    @PutMapping("update")
    public CommonResult update(@RequestBody GoodsCategoryEntity goodsCategoryEntity) {
        goodsCategoryEntity.setUpdateTime(new Date());
        goodsCategoryService.saveOrUpdate(goodsCategoryEntity);
        redisUtil.hDel(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY);
        return CommonResult.success();
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{goodsCategoryId}")
    public CommonResult info(@PathVariable("goodsCategoryId") Long goodsCategoryId){
        GoodsCategoryEntity goodsCategoryEntity = goodsCategoryService.getById(goodsCategoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("goodsCategoryEntity", goodsCategoryEntity);
        return CommonResult.success(map);
    }

    /**
     * 删除
     */
    @DeleteMapping("delete/{goodsCategoryId}")
    public CommonResult delete(@PathVariable("goodsCategoryId") long goodsCategoryId){
        //判断是否有子菜单或按钮
        List<GoodsCategoryEntity> goodsCategoryEntityList = goodsCategoryService.queryListParentId(goodsCategoryId);
        if(goodsCategoryEntityList != null && goodsCategoryEntityList.size() > 0){
            return CommonResult.fail(ResultCodeEnum.PLEASE_DELETE_CHILD_MENU_BUTTON.getCode(),
                    ResultCodeEnum.PLEASE_DELETE_CHILD_MENU_BUTTON.getMessage());
        }
        goodsCategoryService.removeById(goodsCategoryId);
        redisUtil.hDel(RedisKeyConstant.GOODS_CATEGORY_KEY, RedisKeyConstant.GOODS_CATEGORY_HASH_KEY);
        return CommonResult.success();
    }

    /**
     * 获取分组并合并后的商品分类 及 未合并的商品分类
     * @return
     */
    @GetMapping("/getCategoryAndMergedCategory")
    public CommonResult getCategoryAndMergedCategory() {
        Map<String,List<GoodsCategoryEntity>> categoryMap = goodsCategoryService.queryMergeGoodsCategoryTree(0L);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), categoryMap);
    }

    /**
     * 根据父id获取子一级菜单
     * @return
     */
    @GetMapping("getSubFirst")
    public CommonResult getSubFirst(@RequestParam("goodsCategoryId") Long goodsCategoryId) {
        List<GoodsCategoryEntity> goodsCategoryEntityList = goodsCategoryService.queryListParentId(goodsCategoryId);
        return CommonResult.success(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getMessage(), goodsCategoryEntityList);
    }

}
