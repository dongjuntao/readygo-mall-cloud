package com.mall.brand.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 品牌分类表
 * @Date 2021/6/16 9:55
 * @Version 1.0
 */
@Data
@TableName("brand_category")
public class BrandCategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 品牌分类名称
     */
    private String name;
    /**
     * 品牌分类描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
