package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 用户足迹表
 * @Date 2022/4/28 20:48
 * @Version 1.0
 */
@Data
@TableName("footprint")
public class FootprintEntity {

    @TableId
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 关联的商品信息
     */
    @TableField(exist = false)
    private Object footprintGoodsVO;
}
