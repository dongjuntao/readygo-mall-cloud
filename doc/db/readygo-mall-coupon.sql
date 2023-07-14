/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-coupon

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 14/07/2023 14:42:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠券名称',
  `source` int(0) NULL DEFAULT NULL COMMENT '优惠券来源（优惠券来源（0：平台；1：商家））',
  `admin_user_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id',
  `type` int(0) NULL DEFAULT NULL COMMENT '优惠券类型（0：满减券；1：满折券）',
  `use_threshold` int(0) NULL DEFAULT NULL COMMENT '使用门槛（0：无门槛 1：有门槛）',
  `min_consumption` double(10, 2) NULL DEFAULT NULL COMMENT '有门槛时最低消费',
  `discount_amount` double(10, 2) NULL DEFAULT NULL COMMENT '优惠额度（如果是满减券，该字段是减钱数，如果是满折券，该字段是打折数）',
  `use_scope` int(0) NULL DEFAULT NULL COMMENT '使用范围（0：全部商品；1：指定分类， 2：指定商品）',
  `goods_category_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '支持的商品分类ids',
  `goods_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '支持的商品ids',
  `applicable_member` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '适用会员（普通会员，青铜会员，白银会员，黄金会员，铂金会员，钻石会员，最强买家）',
  `issue_number` int(0) NULL DEFAULT NULL COMMENT '发行数量',
  `received_number` int(0) NULL DEFAULT NULL COMMENT '领取数量',
  `per_limit` int(0) NULL DEFAULT NULL COMMENT '每人限领多少张',
  `valid_period_start` datetime(0) NULL DEFAULT NULL COMMENT '有效期（开始）',
  `valid_period_end` datetime(0) NULL DEFAULT NULL COMMENT '有效期（结束）',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（0：禁用；1：启用）',
  `auth_status` int(0) NULL DEFAULT NULL COMMENT '审核状态（0：待审核，1：审核通过，2：审核拒绝）',
  `auth_opinion` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '修改人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES (1, '满100减10', 0, NULL, 0, 1, 100.00, 10.00, 0, NULL, NULL, '普通会员,青铜会员,白银会员,黄金会员,铂金会员,钻石会员,最强买家', 300, 0, 1, '2022-05-01 00:00:00', '2024-06-01 00:00:00', 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `coupon` VALUES (2, '满1000减200', 1, 2, 0, 1, 1000.00, 200.00, 2, '', '2', '普通会员,青铜会员,白银会员,黄金会员,铂金会员,钻石会员,最强买家', 100, 0, 1, '2022-12-01 00:00:00', '2025-01-01 00:00:00', 1, 1, '通过', NULL, NULL, NULL, NULL);
INSERT INTO `coupon` VALUES (3, '满100打8折', 1, 2, 1, 1, 100.00, 8.00, 2, '', '2', '普通会员,青铜会员,白银会员,黄金会员,铂金会员,钻石会员,最强买家', 200, 0, 2, '2023-05-01 00:00:00', '2026-06-01 00:00:00', 1, 1, '成都市', NULL, NULL, NULL, NULL);
INSERT INTO `coupon` VALUES (4, '满1000打9折', 0, NULL, 1, 1, 1000.00, 9.00, 1, '8,11', '', '普通会员,青铜会员,白银会员,黄金会员,铂金会员,钻石会员,最强买家', 500, 0, 1, '2023-05-01 00:00:00', '2026-06-01 00:00:00', 1, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `coupon` VALUES (5, '满500减100', 0, NULL, 0, 1, 500.00, 100.00, 0, NULL, NULL, '普通会员,青铜会员,白银会员,黄金会员,铂金会员,钻石会员,最强买家', 200, 0, 1, '2023-05-01 00:00:00', '2025-06-01 00:00:00', 1, 1, NULL, NULL, NULL, NULL, NULL);

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
