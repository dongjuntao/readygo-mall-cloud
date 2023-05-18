/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-log

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 18/05/2023 21:32:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rabbit_mq_received_log
-- ----------------------------
DROP TABLE IF EXISTS `rabbit_mq_received_log`;
CREATE TABLE `rabbit_mq_received_log`  (
  `message_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` int(0) NULL DEFAULT NULL COMMENT '0:待消费 1:消费成功 2:消费失败',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交换机',
  `route_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由键',
  `queue_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '队列名称',
  `try_count` int(0) NULL DEFAULT NULL COMMENT '重试次数',
  `received_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '接收的消息内容',
  `try_time` datetime(0) NULL DEFAULT NULL COMMENT '重试时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rabbit_mq_send_log
-- ----------------------------
DROP TABLE IF EXISTS `rabbit_mq_send_log`;
CREATE TABLE `rabbit_mq_send_log`  (
  `message_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息id',
  `status` int(0) NULL DEFAULT NULL COMMENT '消息状态（0:发送中，1:已发送，2:发送失败）',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交换机',
  `route_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由键',
  `try_count` int(0) NULL DEFAULT NULL COMMENT '重试次数',
  `send_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '发送的消息内容',
  `cause` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '失败原因',
  `try_time` datetime(0) NULL DEFAULT NULL COMMENT '重试时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发送消息到mq，记录日志信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
