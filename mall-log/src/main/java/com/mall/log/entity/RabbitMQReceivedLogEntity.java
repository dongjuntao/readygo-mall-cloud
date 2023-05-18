package com.mall.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description rabbitmq 消费消息日志
 * @Date 2023/5/15 16:44
 * @Version 1.0
 */
@Data
@TableName("rabbit_mq_received_log")
public class RabbitMQReceivedLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消息id，作为主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String messageId;
    /**
     * 状态（0:待消费 1:消费成功 2:消费失败）
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
     * 队列名称
     */
    private String queueName;
    /**
     * 重试次数【可用于终止重试】
     */
    private Integer tryCount;
    /**
     * 发送的消息内容
     */
    private String receivedContent;
    /**
     * 重试时间
     */
    private Date tryTime;
    /**
     * 创建时间
     */
    private Date createTime;

}
