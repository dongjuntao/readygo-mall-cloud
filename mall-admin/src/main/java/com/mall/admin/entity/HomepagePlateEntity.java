package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author DongJunTao
 * @Description 商城首页板块
 * @Date 2022/2/10 21:55
 * @Version 1.0
 */
@Data
@TableName("homepage_plate")
public class HomepagePlateEntity {

    @TableId
    private Long id;

    /**
     * 板块名称
     */
    private String name;

    /**
     */
    private String type;

    /**
     * 板块二级名称
     */
    private String secondName;

    /**
     * 板块背景色
     */
    private String bgColor;

    /**
     * 板块内最大容纳商品数量
     */
    private Integer maxLimit;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 所选的商品列表
     */
    @TableField(exist = false)
    private List<Object> list;
}
