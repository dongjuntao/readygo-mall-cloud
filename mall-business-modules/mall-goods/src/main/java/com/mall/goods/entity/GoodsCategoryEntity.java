package com.mall.goods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.goods.vo.GoodsCategoryInfoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 商品分类表
 * @Date 2021/6/22 10:58
 * @Version 1.0
 */
@Data
@TableName("goods_category")
public class GoodsCategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 父分类名称
     */
    @TableField(exist=false)
    private String parentName;
    /**
     * 商品分类名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 分组号（用于商城前台分类的合并展示）
     */
    private Integer groupNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 子节点
     */
    @TableField(exist=false)
    private List<GoodsCategoryEntity> children = new ArrayList<>();
    /**
     * 商品分类名称（多个，以分隔符分开）
     */
    @TableField(exist=false)
    private List<GoodsCategoryInfoVO> goodsCategoryInfoList;
}
