package com.mall.log.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 数据传递参数
 * @Date 2023/5/15 22:02
 * @Version 1.0
 */
@Data
public class RabbitMQSendLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消息id，作为主键
     */
    private String messageId;
    /**
     * 状态（0:发送中 1:发送成功 2:发送失败）
     */
    private Integer status;
    /**
     * 交换机
     */
    private String exchange;
    /**
     * 路由键
     */
    private String routeKey;
    /**
     * 重试次数【可用于终止重试】
     */
    private Integer tryCount;
    /**
     * 发送的消息内容
     */
    private String sendContent;
    /**
     * 失败原因
     */
    private String cause;
    /**
     * 重试时间
     */
    private Date tryTime;
    /**
     * 创建时间
     */
    private Date createTime;
}
