/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : readygo-mall-member

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/08/2022 15:59:26
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
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员商品收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect_goods
-- ----------------------------

-- ----------------------------
-- Table structure for collect_shop
-- ----------------------------
DROP TABLE IF EXISTS `collect_shop`;
CREATE TABLE `collect_shop`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商家id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect_shop
-- ----------------------------
INSERT INTO `collect_shop` VALUES (21, 1, 2);
INSERT INTO `collect_shop` VALUES (23, 1, 4);

-- ----------------------------
-- Table structure for coupon_received
-- ----------------------------
DROP TABLE IF EXISTS `coupon_received`;
CREATE TABLE `coupon_received`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '商家发放的优惠券id',
  `use_status` int(0) NULL DEFAULT NULL COMMENT '使用状态：0:新创建  1:已使用  2:已过期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '领取时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '我的优惠券' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_received
-- ----------------------------
INSERT INTO `coupon_received` VALUES (3, 1, 1, 0, '2022-06-04 22:24:50');
INSERT INTO `coupon_received` VALUES (4, 1, 2, 0, '2022-06-04 22:24:58');
INSERT INTO `coupon_received` VALUES (5, 1, 4, 1, '2022-06-04 22:25:06');
INSERT INTO `coupon_received` VALUES (6, 1, 5, 1, '2022-06-04 22:25:16');
INSERT INTO `coupon_received` VALUES (7, 1, 3, 1, '2022-06-04 22:26:13');
INSERT INTO `coupon_received` VALUES (8, 1, 3, 1, '2022-06-04 22:26:17');

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
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of footprint
-- ----------------------------
INSERT INTO `footprint` VALUES (71, 1, 4, 2, '2022-07-22 14:59:13', '2022-07-22 15:43:39');
INSERT INTO `footprint` VALUES (72, 1, 5, 4, '2022-07-22 15:00:30', '2022-07-22 15:43:45');
INSERT INTO `footprint` VALUES (73, 1, 2, 2, '2022-07-22 15:00:36', '2022-07-22 15:43:50');
INSERT INTO `footprint` VALUES (74, 1, 1, 2, '2022-07-22 15:00:38', '2022-07-26 09:37:30');
INSERT INTO `footprint` VALUES (75, 1, 3, 2, '2022-07-22 15:51:41', '2022-07-22 17:52:34');

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (1, 'test', '$2a$10$dx0qKrlQGEKGa8AF4VouFOSOw5ncEUiX5xorbd3wgdBrofT3GcXrO', '大董', 'tom', 0, '18888888888', '342933877266566715', '2022-07-06', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/member/avatar1650361266276458099xiaomi.jpg', '2022-04-15 19:12:40', '2022-07-08 15:42:14');
INSERT INTO `member` VALUES (2, 'test2', '$2a$10$5emJJ60wCCESYnCTSK2AM.TekTPA1f1HOXXA2nTg7iq8scvj9CmY.', '小董', 'tom', 0, '15726553345', NULL, '2022-04-07', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/member/avatar1650377712122635512apple.jpg', '2022-04-15 21:04:03', '2022-05-03 17:41:44');

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
INSERT INTO `recipient_info` VALUES (7, 1, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '795,796,807', '家', 1, '2022-07-07 17:03:05', NULL);

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
