/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-seckill

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 30/08/2023 15:51:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill_config
-- ----------------------------
DROP TABLE IF EXISTS `seckill_config`;
CREATE TABLE `seckill_config`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '秒杀配置名称',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `admin_user_id` bigint(0) NULL DEFAULT NULL COMMENT '所属商户id',
  `seckill_start_date` date NULL DEFAULT NULL COMMENT '秒杀开始日期（某天）',
  `seckill_end_date` date NULL DEFAULT NULL COMMENT '秒杀结束日期（某天）',
  `seckill_start_time` time(0) NULL DEFAULT NULL COMMENT '秒杀开始时间点',
  `seckill_end_time` time(0) NULL DEFAULT NULL COMMENT '秒杀结束时间点',
  `per_limit` int(0) NULL DEFAULT NULL COMMENT '每人限购次数',
  `auth_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态（0：待审核，1：审核通过，2：审核拒绝）',
  `auth_opinion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_config
-- ----------------------------
INSERT INTO `seckill_config` VALUES (3, '格力（GREE）抽油烟机 塔型大油烟机大吸力/大风压/低噪油烟机', 11, 2, '2022-08-01', '2024-09-06', '00:00:00', '02:00:00', 1, 'AUDIT_SUCCESS', '', 1, NULL, '2022-09-13 15:29:40', '2023-07-13 14:18:41');
INSERT INTO `seckill_config` VALUES (4, '【新国标】格力空调大1.5p匹变频挂机冷暖家用官方旗舰店官网天丽', 8, 2, '2022-09-04', '2024-10-01', '06:00:00', '08:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:31:06', NULL);
INSERT INTO `seckill_config` VALUES (5, 'TCL 55V6E 55英寸 金属全面屏 2+16GB 低蓝光护眼 双频WiFi 超薄液晶55吋平板电视机 智慧屏', 6, 2, '2022-07-01', '2024-08-01', '08:00:00', '10:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:31:52', NULL);
INSERT INTO `seckill_config` VALUES (6, 'HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', 4, 2, '2022-09-01', '2024-10-01', '10:00:00', '12:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:32:37', NULL);
INSERT INTO `seckill_config` VALUES (7, '佳能5d4 EOS 5D Mark IV单机身专业级单反相机24-105f4 USM全画幅', 3, 2, '2022-09-01', '2024-10-01', '12:00:00', '14:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:33:24', NULL);
INSERT INTO `seckill_config` VALUES (8, '当天发【24期免息】Mate 40E手机TD M40 5G官方旗舰店正品新款官网pro直降p50系列鸿蒙nova9 华5g为Huawei', 2, 2, '2022-09-01', '2024-10-01', '14:00:00', '16:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:34:22', NULL);
INSERT INTO `seckill_config` VALUES (9, '【首降250元！指定时间点疯抢五折！】Xiaomi 12S Pro游戏徕卡拍照骁龙8+小米12spro官方旗舰店红米小米手机', 1, 2, '2022-09-01', '2024-10-01', '16:00:00', '18:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:35:17', NULL);
INSERT INTO `seckill_config` VALUES (10, '东来也适用于华为P50Pro手机壳P50新款镜头全包防摔p50e国潮p40pro原创p40超薄p30pro创意pro+中国风保护套', 13, 3, '2022-09-01', '2024-10-01', '18:00:00', '20:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:35:48', NULL);
INSERT INTO `seckill_config` VALUES (11, '小米米家胶囊咖啡机家用全自动小型研磨一体迷你胶囊机办公煮咖啡', 12, 3, '2022-09-01', '2024-10-01', '20:00:00', '22:00:00', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:36:12', NULL);
INSERT INTO `seckill_config` VALUES (12, '格力晶弘 变频冰箱 529升对开门风冷无霜-33度深冷冻BCD-529WPDC', 10, 4, '2022-09-01', '2024-10-01', '22:00:00', '23:59:59', 1, 'AUDIT_SUCCESS', '', NULL, NULL, '2022-09-13 15:36:39', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_goods_sku
-- ----------------------------
INSERT INTO `seckill_goods_sku` VALUES (7, 4, 100, 2499.00, 200.00);
INSERT INTO `seckill_goods_sku` VALUES (8, 5, 96, 5199.00, 300.00);
INSERT INTO `seckill_goods_sku` VALUES (9, 6, 121, 4299.00, 500.00);
INSERT INTO `seckill_goods_sku` VALUES (10, 6, 122, 5299.00, 1000.00);
INSERT INTO `seckill_goods_sku` VALUES (11, 6, 123, 6199.00, 1000.00);
INSERT INTO `seckill_goods_sku` VALUES (12, 6, 124, 7099.00, 2000.00);
INSERT INTO `seckill_goods_sku` VALUES (13, 7, 81, 10999.00, 200.00);
INSERT INTO `seckill_goods_sku` VALUES (14, 7, 82, 11999.00, 500.00);
INSERT INTO `seckill_goods_sku` VALUES (15, 7, 83, 10999.00, 1000.00);
INSERT INTO `seckill_goods_sku` VALUES (16, 7, 84, 11999.00, 2000.00);
INSERT INTO `seckill_goods_sku` VALUES (17, 8, 113, 3599.00, 50.00);
INSERT INTO `seckill_goods_sku` VALUES (18, 8, 114, 4299.00, 100.00);
INSERT INTO `seckill_goods_sku` VALUES (19, 8, 115, 6199.00, 100.00);
INSERT INTO `seckill_goods_sku` VALUES (20, 8, 116, 6799.00, 200.00);
INSERT INTO `seckill_goods_sku` VALUES (21, 9, 117, 2299.00, 50.00);
INSERT INTO `seckill_goods_sku` VALUES (22, 9, 118, 2588.00, 200.00);
INSERT INTO `seckill_goods_sku` VALUES (23, 9, 119, 2499.00, 800.00);
INSERT INTO `seckill_goods_sku` VALUES (24, 9, 120, 2788.00, 1000.00);
INSERT INTO `seckill_goods_sku` VALUES (25, 10, 105, 49.00, 2000.00);
INSERT INTO `seckill_goods_sku` VALUES (26, 11, 104, 299.00, 500.00);
INSERT INTO `seckill_goods_sku` VALUES (27, 12, 102, 2999.00, 1000.00);
INSERT INTO `seckill_goods_sku` VALUES (31, 3, 130, 4888.00, 200.00);

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
