package com.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 会员表
 * @Date 2022/4/14 20:30
 * @Version 1.0
 */
@Data
@TableName("member")
public class MemberEntity {

    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别【0：男  1：女】
     */
    private Integer sex;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
