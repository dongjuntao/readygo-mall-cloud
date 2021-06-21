package com.mall.brand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 品牌表
 * @Date 2021/6/16 9:55
 * @Version 1.0
 */
@Data
@TableName("brand")
public class BrandEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 品牌描述
     */
    private String description;
    /**
     * 品牌logo
     */
    private String logo;
    /**
     * 品牌分类id
     */
    private Long brandCategoryId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 排序字段
     */
    private int orderNum;
}
