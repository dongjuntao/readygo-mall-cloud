/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : readygo-mall-cart

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/08/2022 15:58:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 347 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (347, 1, 2);

-- ----------------------------
-- Table structure for cart_goods
-- ----------------------------
DROP TABLE IF EXISTS `cart_goods`;
CREATE TABLE `cart_goods`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cart_id` bigint(0) NULL DEFAULT NULL COMMENT '购物车id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `goods_sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品skuId',
  `count` int(0) NULL DEFAULT NULL COMMENT '购买数量',
  `checked` tinyint(1) NULL DEFAULT NULL COMMENT '是否已被选中',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '加入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 450 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart_goods
-- ----------------------------
INSERT INTO `cart_goods` VALUES (450, 347, 3, 82, 1, 1, '2022-07-22 15:51:44');

SET FOREIGN_KEY_CHECKS = 1;
