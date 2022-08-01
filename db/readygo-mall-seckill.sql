/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : readygo-mall-seckill

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/08/2022 15:59:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill_config
-- ----------------------------
DROP TABLE IF EXISTS `seckill_config`;
CREATE TABLE `seckill_config`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `admin_user_id` bigint(0) NULL DEFAULT NULL COMMENT '所属商户id',
  `seckill_start_date` date NULL DEFAULT NULL COMMENT '秒杀开始日期（某天）',
  `seckill_end_date` date NULL DEFAULT NULL COMMENT '秒杀结束日期（某天）',
  `seckill_start_time` time(0) NULL DEFAULT NULL COMMENT '秒杀开始时间点',
  `seckill_end_time` time(0) NULL DEFAULT NULL COMMENT '秒杀结束时间点',
  `per_limit` int(0) NULL DEFAULT NULL COMMENT '每人限购次数',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（0：禁用；1：启用）',
  `auth_status` int(0) NULL DEFAULT NULL COMMENT '审核状态（0：待审核，1：审核通过，2：审核拒绝）',
  `auth_opinion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_config
-- ----------------------------
INSERT INTO `seckill_config` VALUES (6, 3, 2, '2022-03-02', '2022-04-14', '16:00:00', '18:00:00', 1, 1, 0, NULL, 1, NULL, '2022-03-23 17:43:33', '2022-03-24 10:55:27');

-- ----------------------------
-- Table structure for seckill_goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods_sku`;
CREATE TABLE `seckill_goods_sku`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `seckill_config_id` bigint(0) NULL DEFAULT NULL COMMENT '关联秒杀配置表id',
  `goods_sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品sku id',
  `seckill_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '秒杀价',
  `seckill_stock` decimal(10, 2) NULL DEFAULT NULL COMMENT '秒杀库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_goods_sku
-- ----------------------------
INSERT INTO `seckill_goods_sku` VALUES (25, 6, 15, 32.00, 32.00);
INSERT INTO `seckill_goods_sku` VALUES (26, 6, 16, 543.00, 32.00);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(0) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(0) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
