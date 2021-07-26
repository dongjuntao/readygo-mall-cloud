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
 * @Description 后台用户表
 * @Date 2021/5/28 9:38
 * @Version 1.0
 */
@Data
@TableName("admin_user")
public class AdminUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键id
     */
    @TableId
    private Long id;
    /**
     * 后台用户名称
     */
    private String name;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 管理员类型用户类型（0:系统管理员；1:商户管理员）
     */
    private Integer userType;
    /**
     * 角色ID列表
     */
    @TableField(exist=false)
    private List<Long> roleIdList;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
