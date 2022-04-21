package com.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author DongJunTao
 * @Description 角色表
 * @Date 2021/6/1 10:36
 * @Version 1.0
 */
@Data
@TableName("role")
public class RoleEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色关联的菜单id列表
     */
    @TableField(exist=false)
    private List<Long> menuIdList;

}
