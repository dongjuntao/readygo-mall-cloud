/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-order

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 14/07/2023 14:42:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coupon_selected
-- ----------------------------
DROP TABLE IF EXISTS `coupon_selected`;
CREATE TABLE `coupon_selected`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `received_coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '领取id',
  `is_del` tinyint(0) NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_selected
-- ----------------------------

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '发票内容',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id',
  `invoice_type` int(0) NULL DEFAULT NULL COMMENT '发票类型 0:电子普通发票 1:增值税发票',
  `invoice_title_type` int(0) NULL DEFAULT NULL COMMENT '发票抬头 0：个人 1：单位',
  `invoice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人名称或单位名称',
  `tax_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纳税人识别号',
  `invoice_content` int(0) NULL DEFAULT NULL COMMENT '发票内容 0:商品明细 1:商品类别',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人手机',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoice
-- ----------------------------
INSERT INTO `invoice` VALUES (8, 1, 1, 1, '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` bigint(0) NULL DEFAULT NULL COMMENT '父订单id',
  `order_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父订单号',
  `sub_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子订单号',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `goods_sku_id` bigint(0) NULL DEFAULT NULL COMMENT '商品sku id',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_selling_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品销售价格',
  `goods_count` int(0) NULL DEFAULT NULL COMMENT '商品数量',
  `goods_sub_total` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品小计（价格）',
  `after_sales_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '售后状态',
  `comment_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 1, 'O1663741467958579200', 'S1663741468096991232', 12, 129, '小米米家胶囊咖啡机家用全自动小型研磨一体迷你胶囊机办公煮咖啡', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659887199958373681662705323526413895xm4.jpg', 349.00, 1, '349', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (2, 2, 'O1663741687295512576', 'S1663741687362621440', 13, 128, '东来也适用于华为P50Pro手机壳P50新款镜头全包防摔p50e国潮p40pro原创p40超薄p30pro创意pro+中国风保护套', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659886962939813931662705689912137118dly1.jpg', 69.00, 1, '69', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (3, 3, 'O1668510880045862912', 'S1668510880393990144', 12, 129, '小米米家胶囊咖啡机家用全自动小型研磨一体迷你胶囊机办公煮咖啡', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659887199958373681662705323526413895xm4.jpg', 349.00, 1, '349', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (4, 4, 'O1678767238368333824', 'S1678767238536105984', 1, 117, '【首降250元！指定时间点疯抢五折！】Xiaomi 12S Pro游戏徕卡拍照骁龙8+小米12spro官方旗舰店红米小米手机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599892005820252Qzc-AnSXiWhJKiZIejeadQ.jpg', 2699.00, 1, '2699', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (5, 5, 'O1678767768238952448', 'S1678767768381558784', 18, 138, '特步女鞋板鞋2023新款夏季运动休闲鞋官方正品低帮百搭平底小', NULL, 89.00, 1, '89', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (6, 6, 'O1678768724431212544', 'S1678768724544458752', 16, 136, '巴拉巴柆女童春装卫衣儿童洋气女孩春秋长袖碎花蝴蝶蕾丝边上衣', NULL, 39.90, 1, '39.9', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (7, 7, 'O1678769064111116288', 'S1678769064211779584', 13, 128, '东来也适用于华为P50Pro手机壳P50新款镜头全包防摔p50e国潮p40pro原创p40超薄p30pro创意pro+中国风保护套', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659886962939813931662705689912137118dly1.jpg', 69.00, 1, '69', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (8, 8, 'O1678769120922963968', 'S1678769121023627264', 2, 113, '当天发【24期免息】Mate 40E手机TD M40 5G官方旗舰店正品新款官网pro直降p50系列鸿蒙nova9 华5g为Huawei', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599971761640690Qzc-AnSXiWhJKiZIejeadQ.jpg', 3999.00, 1, '3999', 'NEW', 'NEW');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `trade_id` bigint(0) NULL DEFAULT NULL COMMENT '交易id',
  `trade_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易号',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id(买家id)',
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员名称(买家名称)',
  `merchant_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id(卖家id)',
  `merchant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户名称(卖家名称)',
  `source` int(0) NULL DEFAULT NULL COMMENT '订单来源（0：PC端，1：移动端，2：小程序）',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态, UNPAID(\"待付款\"), PAID(\"已付款\"), UNDELIVERED(\"待发货\"), DELIVERED(\"已发货\"), FINISHED(\"已完成\"),  CANCELLED(\"已取消\");',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取消订单原因',
  `pay_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式，支付宝：ALIPAY, 微信：WECHAT_PAY',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品总金额',
  `freight` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `final_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '最终金额（应付金额）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '下单时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '订单更新时间',
  `recipient_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `recipient_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人详细地址',
  `recipient_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人手机号',
  `region_names` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (1, 'O1663741467958579200', 1, 'T1663741467815972864', 1, 'test', 0, '平台自营商户', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 349.00, 0.00, 349.00, '2023-02-27 10:57:31', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (2, 'O1663741687295512576', 2, 'T1663741687232598016', 1, 'test', 3, '平台自营商户', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 69.00, 0.00, 69.00, '2023-03-31 10:58:24', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (3, 'O1668510880045862912', 3, 'T1668510879668375552', 1, 'test', 0, '平台自营商户', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 349.00, 0.00, 349.00, '2023-04-13 14:49:28', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888881', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (4, 'O1678767238368333824', 4, 'T1678767238158618624', 1, 'test', 0, '测试商户1', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 2699.00, 0.00, 2699.00, '2023-05-11 22:04:34', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888881', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (5, 'O1678767768238952448', 5, 'T1678767768125706240', 1, 'test', 0, '测试商户2', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 89.00, 0.00, 89.00, '2023-05-11 22:06:41', NULL, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '江苏省 苏州市 相城区');
INSERT INTO `order_info` VALUES (6, 'O1678768724431212544', 6, 'T1678768724322160640', 1, 'test', 0, '测试商户1', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 39.90, 0.00, 39.90, '2023-06-11 22:10:29', NULL, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '江苏省 苏州市 相城区');
INSERT INTO `order_info` VALUES (7, 'O1678769064111116288', 7, 'T1678769063976898560', 1, 'test', 0, '平台自营商户1', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 69.00, 0.00, 69.00, '2023-07-11 22:11:50', NULL, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '江苏省 苏州市 相城区');
INSERT INTO `order_info` VALUES (8, 'O1678769120922963968', 8, 'T1678769120788746240', 1, 'test', 0, '测试商户1', 0, 'FINISHED', NULL, 'ALIPAY', NULL, NULL, 3999.00, 0.00, 3999.00, '2023-07-11 22:12:03', NULL, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '江苏省 苏州市 相城区');

-- ----------------------------
-- Table structure for order_invoice
-- ----------------------------
DROP TABLE IF EXISTS `order_invoice`;
CREATE TABLE `order_invoice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` int(0) NULL DEFAULT NULL COMMENT '订单id',
  `invoice_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票类型 0:电子普通发票 1:增值税发票',
  `invoice_title_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头类型 0：个人 1：单位',
  `invoice_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人名称或单位名称',
  `tax_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纳税人识别号',
  `invoice_content` int(0) NULL DEFAULT NULL COMMENT '发票内容 0:商品明细 1:商品类别',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人手机号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_invoice
-- ----------------------------
INSERT INTO `order_invoice` VALUES (1, 1, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (2, 2, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (3, 3, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (4, 4, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (5, 5, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (6, 6, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (7, 7, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (8, 8, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');

-- ----------------------------
-- Table structure for recipient_info_selected
-- ----------------------------
DROP TABLE IF EXISTS `recipient_info_selected`;
CREATE TABLE `recipient_info_selected`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `recipient_info_id` bigint(0) NULL DEFAULT NULL COMMENT '收货信息id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recipient_info_selected
-- ----------------------------
INSERT INTO `recipient_info_selected` VALUES (3, 1, 6);

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易号',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id(买家id)',
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员名称(买家名称)',
  `pay_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款状态',
  `trade_time` datetime(0) NULL DEFAULT NULL COMMENT '交易时间',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品总金额',
  `freight` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `final_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '最终金额（应付金额）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trade
-- ----------------------------
INSERT INTO `trade` VALUES (1, 'T1663741467815972864', 1, 'test', 'UNPAID', '2023-05-31 10:57:31', 349.00, NULL, 349.00);
INSERT INTO `trade` VALUES (2, 'T1663741687232598016', 1, 'test', 'UNPAID', '2023-05-31 10:58:24', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (3, 'T1668510879668375552', 1, 'test', 'UNPAID', '2023-06-13 14:49:28', 349.00, NULL, 349.00);
INSERT INTO `trade` VALUES (4, 'T1678767238158618624', 1, 'test', 'UNPAID', '2023-07-11 22:04:34', 2699.00, NULL, 2699.00);
INSERT INTO `trade` VALUES (5, 'T1678767768125706240', 1, 'test', 'UNPAID', '2023-07-11 22:06:41', 89.00, NULL, 89.00);
INSERT INTO `trade` VALUES (6, 'T1678768724322160640', 1, 'test', 'UNPAID', '2023-07-11 22:10:29', 39.90, NULL, 39.90);
INSERT INTO `trade` VALUES (7, 'T1678769063976898560', 1, 'test', 'UNPAID', '2023-07-11 22:11:50', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (8, 'T1678769120788746240', 1, 'test', 'UNPAID', '2023-07-11 22:12:03', 3999.00, NULL, 3999.00);

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
