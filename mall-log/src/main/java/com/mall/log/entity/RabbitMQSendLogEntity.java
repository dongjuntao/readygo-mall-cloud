package com.mall.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description rabbitmq 发送消息日志
 * @Date 2023/5/15 16:44
 * @Version 1.0
 */
@Data
@TableName("rabbit_mq_send_log")
public class RabbitMQSendLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消息id，作为主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
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
