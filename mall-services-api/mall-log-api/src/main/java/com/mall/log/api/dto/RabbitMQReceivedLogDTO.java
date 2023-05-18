package com.mall.log.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class RabbitMQReceivedLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消息id，作为主键
     */
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
