/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-member

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 14/07/2023 14:42:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collect_goods
-- ----------------------------
DROP TABLE IF EXISTS `collect_goods`;
CREATE TABLE `collect_goods`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员商品收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect_goods
-- ----------------------------
INSERT INTO `collect_goods` VALUES (41, 1, 1, 2);

-- ----------------------------
-- Table structure for collect_shop
-- ----------------------------
DROP TABLE IF EXISTS `collect_shop`;
CREATE TABLE `collect_shop`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商家id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect_shop
-- ----------------------------
INSERT INTO `collect_shop` VALUES (21, 1, 2);
INSERT INTO `collect_shop` VALUES (24, 1, 4);

-- ----------------------------
-- Table structure for coupon_received
-- ----------------------------
DROP TABLE IF EXISTS `coupon_received`;
CREATE TABLE `coupon_received`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '商家发放的优惠券id',
  `use_status` int(0) NULL DEFAULT NULL COMMENT '使用状态：0:新创建  1:已使用  2:已过期 3:已冻结(使用后未付款)',
  `valid_period_start` datetime(0) NULL DEFAULT NULL COMMENT '优惠券有效期（开始）',
  `valid_period_end` datetime(0) NULL DEFAULT NULL COMMENT '优惠券有效期（结束）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '领取时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '我的优惠券' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_received
-- ----------------------------
INSERT INTO `coupon_received` VALUES (1, 1, 1, 0, '2022-05-01 00:00:00', '2024-06-01 00:00:00', '2023-05-09 16:52:38', NULL);

-- ----------------------------
-- Table structure for footprint
-- ----------------------------
DROP TABLE IF EXISTS `footprint`;
CREATE TABLE `footprint`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '记录创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 219 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of footprint
-- ----------------------------
INSERT INTO `footprint` VALUES (146, 2, 4, 2, '2023-05-31 14:13:44', NULL);
INSERT INTO `footprint` VALUES (147, 2, 12, 3, '2023-05-31 14:13:44', NULL);
INSERT INTO `footprint` VALUES (148, 2, 3, 2, '2023-05-31 14:13:44', NULL);
INSERT INTO `footprint` VALUES (197, 1, 5, 4, '2023-06-06 17:01:28', '2023-06-14 20:49:57');
INSERT INTO `footprint` VALUES (198, 1, 4, 2, '2023-06-06 17:07:21', '2023-07-11 14:06:20');
INSERT INTO `footprint` VALUES (199, 1, 3, 2, '2023-06-13 14:48:53', '2023-06-14 20:49:56');
INSERT INTO `footprint` VALUES (200, 1, 10, 4, '2023-06-13 14:48:54', '2023-06-14 20:49:55');
INSERT INTO `footprint` VALUES (202, 1, 12, 3, '2023-06-13 14:49:21', '2023-06-14 20:49:55');
INSERT INTO `footprint` VALUES (203, 1, 8, 2, '2023-06-13 15:59:10', '2023-07-07 17:01:39');
INSERT INTO `footprint` VALUES (204, 1, 14, 5, '2023-06-13 15:59:12', '2023-06-14 20:49:55');
INSERT INTO `footprint` VALUES (206, 1, 11, 2, '2023-06-13 16:39:08', '2023-06-14 20:49:48');
INSERT INTO `footprint` VALUES (207, 1, 7, 4, '2023-06-13 16:39:11', '2023-06-14 20:49:50');
INSERT INTO `footprint` VALUES (208, 1, 2, 2, '2023-06-13 16:39:13', '2023-07-11 22:12:01');
INSERT INTO `footprint` VALUES (209, 1, 6, 2, '2023-06-13 16:39:18', '2023-06-14 20:49:56');
INSERT INTO `footprint` VALUES (210, 9, 8, 2, '2023-06-14 20:50:00', NULL);
INSERT INTO `footprint` VALUES (211, 9, 67, 2, '2023-06-21 14:08:05', '2023-06-21 14:08:08');
INSERT INTO `footprint` VALUES (212, 9, 16, 2, '2023-06-25 16:07:11', NULL);
INSERT INTO `footprint` VALUES (213, 1, 17, 2, '2023-07-07 17:01:02', '2023-07-07 17:01:33');
INSERT INTO `footprint` VALUES (214, 1, 16, 2, '2023-07-07 17:01:37', '2023-07-11 22:10:25');
INSERT INTO `footprint` VALUES (215, 1, 15, 5, '2023-07-07 17:01:47', '2023-07-07 17:06:53');
INSERT INTO `footprint` VALUES (216, 1, 1, 2, '2023-07-07 17:07:20', '2023-07-11 22:04:32');
INSERT INTO `footprint` VALUES (217, 1, 18, 4, '2023-07-11 22:04:49', '2023-07-11 22:06:22');
INSERT INTO `footprint` VALUES (218, 1, 13, 3, '2023-07-11 22:11:44', '2023-07-11 22:11:47');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '会员主键id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` int(0) NULL DEFAULT NULL COMMENT '性别（0：男，1：女）',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, 'test', '$2a$10$8//5kSsttPB6uZM8Xl/DJ.36kIqvc5jpcZJFNnUDd5ipYEJ0nvA0m', '测试买家', '隔壁老王', 0, '18888888888', '342933877266566715', '2022-07-06', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/member/avatar1667272764516802261touxiang.jpg', '2022-04-15 19:12:40', '2022-11-01 11:19:28');
INSERT INTO `member` VALUES (2, 'test2', '$2a$10$nOA3z9YkpfhXWRF44m9dKeDoHnTwDawJHo21JlJHU.//poRTArfKu', '测试买家', '武大郎', 0, '15726553345', NULL, '2022-04-07', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/member/avatar1650377712122635512apple.jpg', '2022-04-15 21:04:03', '2022-09-14 10:28:49');

-- ----------------------------
-- Table structure for recipient_info
-- ----------------------------
DROP TABLE IF EXISTS `recipient_info`;
CREATE TABLE `recipient_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `regions` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地区id（省、市、区县）如（100,101,102）',
  `address_alias` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址别名',
  `is_default` tinyint(0) NULL DEFAULT NULL COMMENT '是否默认',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收货信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recipient_info
-- ----------------------------
INSERT INTO `recipient_info` VALUES (2, 1, '赵梅', '淝河乡崔园', '18866666666', '1024,1044,1049', '家', 0, '2022-04-18 17:46:09', NULL);
INSERT INTO `recipient_info` VALUES (6, 1, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '813,852,855', '政府机构', 0, '2022-05-28 14:41:25', NULL);
INSERT INTO `recipient_info` VALUES (7, 1, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888881', '795,796,807', '家', 1, '2022-07-07 17:03:05', NULL);

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
