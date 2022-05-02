package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.member.vo.CollectShopVO;
import lombok.Data;

/**
 * @Author DongJunTao
 * @Description 店铺收藏表
 * @Date 2022/5/2 10:45
 * @Version 1.0
 */
@Data
@TableName("collect_shop")
public class CollectShopEntity {

    @TableField
    private Long id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 商户id
     */
    private Long merchantId;
    /**
     * 关联的商户信息
     */
    @TableField(exist = false)
    private CollectShopVO collectShopVO;
}
