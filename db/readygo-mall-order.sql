/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : readygo-mall-order

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/08/2022 15:59:34
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
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (40, 43, 'O1549315192398155776', 'S1549315192452681728', 4, 77, '华为/HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600106407320786t-gvMHnbgVq9ZzL2HItykA.jpg', 4899.00, 2, '9798', 'NOT_APPLIED', 'NEW');
INSERT INTO `order_detail` VALUES (41, 44, 'O1549318424184885248', 'S1549318424247799808', 5, 75, 'Apple 苹果 MacBook Air 2020新款', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597533765523084PFEFLnSQyU1651911291903.png', 9999.00, 2, '19998', 'NOT_APPLIED', 'NEW');
INSERT INTO `order_detail` VALUES (42, 45, 'O1549757635123875840', 'S1549757635258093568', 4, 77, '华为/HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600106407320786t-gvMHnbgVq9ZzL2HItykA.jpg', 4899.00, 1, '4899', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (43, 46, 'O1549757635492974592', 'S1549757635526529024', 5, 73, 'Apple 苹果 MacBook Air 2020新款', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597528758451679PFEFLnSQyU1651911291903.png', 8799.00, 1, '8799', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (44, 47, 'O1549773634317455360', 'S1549773634451673088', 4, 77, '华为/HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600106407320786t-gvMHnbgVq9ZzL2HItykA.jpg', 4899.00, 1, '4899', 'NOT_APPLIED', 'NEW');
INSERT INTO `order_detail` VALUES (45, 48, 'O1550321102503415808', 'S1550321102541164544', 4, 77, '华为/HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600106407320786t-gvMHnbgVq9ZzL2HItykA.jpg', 4899.00, 1, '4899', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (46, 48, 'O1550321102503415808', 'S1550321102541164545', 1, 89, '小米12pro', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599892005820252Qzc-AnSXiWhJKiZIejeadQ.jpg', 2699.00, 1, '2699', 'NEW', 'NEW');
INSERT INTO `order_detail` VALUES (47, 49, 'O1550321102654410752', 'S1550321102666993664', 5, 73, 'Apple 苹果 MacBook Air 2020新款', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597528758451679PFEFLnSQyU1651911291903.png', 8799.00, 1, '8799', 'NEW', 'NEW');

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
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (43, 'O1549315192398155776', 40, 'T1549315192226189312', 1, 'test', 0, '入驻商户', 0, 'CANCELLED', '拍错了', 'ALIPAY', '2022-07-19 16:48:44', NULL, 9798.00, 0.00, 9798.00, '2022-07-19 16:48:21', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (44, 'O1549318424184885248', 41, 'T1549318423996141568', 1, 'test', 0, '入驻商户2', 0, 'PAID', NULL, 'ALIPAY', NULL, NULL, 19998.00, 0.00, 19998.00, '2022-07-19 17:01:12', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (45, 'O1549757635123875840', 42, 'T1549757634721222656', 1, 'test', 2, '入驻商户', 0, 'CANCELLED', '买重复了', 'ALIPAY', NULL, NULL, 4899.00, 0.00, 4899.00, '2022-07-20 22:06:28', NULL, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '江苏省 苏州市 相城区');
INSERT INTO `order_info` VALUES (46, 'O1549757635492974592', 42, 'T1549757634721222656', 1, 'test', 4, '入驻商户2', 0, 'CANCELLED', '拍错了', 'ALIPAY', NULL, NULL, 8799.00, 0.00, 8799.00, '2022-07-20 22:06:28', NULL, '黄凯', '南湖办公大楼315室(南湖东路30号)', '15888888888', '江苏省 苏州市 相城区');
INSERT INTO `order_info` VALUES (47, 'O1549773634317455360', 43, 'T1549773634019659776', 1, 'test', 2, '入驻商户', 0, 'PAID', NULL, 'ALIPAY', NULL, NULL, 4899.00, 0.00, 4899.00, '2022-07-20 23:10:02', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (48, 'O1550321102503415808', 44, 'T1550321102432112640', 1, 'test', 2, '入驻商户', 0, 'UNPAID', NULL, 'ALIPAY', NULL, NULL, 7598.00, 0.00, 7598.00, '2022-07-22 11:25:29', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');
INSERT INTO `order_info` VALUES (49, 'O1550321102654410752', 44, 'T1550321102432112640', 1, 'test', 4, '入驻商户2', 0, 'UNPAID', NULL, 'ALIPAY', NULL, NULL, 8799.00, 0.00, 8799.00, '2022-07-22 11:25:29', NULL, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888888', '上海市 上海市 浦东新区');

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
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_invoice
-- ----------------------------
INSERT INTO `order_invoice` VALUES (34, 38, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (35, 39, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (36, 40, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (37, 41, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (38, 42, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (39, 43, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (40, 44, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (41, 45, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (42, 46, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (43, 47, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (44, 48, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (45, 49, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');

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
INSERT INTO `recipient_info_selected` VALUES (3, 1, 7);

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
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trade
-- ----------------------------
INSERT INTO `trade` VALUES (40, 'T1549315192226189312', 1, 'test', 'UNPAID', '2022-07-19 16:48:21', 9798.00, NULL, 9798.00);
INSERT INTO `trade` VALUES (41, 'T1549318423996141568', 1, 'test', 'PAID', '2022-07-19 17:01:12', 19998.00, NULL, 19998.00);
INSERT INTO `trade` VALUES (42, 'T1549757634721222656', 1, 'test', 'UNPAID', '2022-07-20 22:06:28', 13698.00, NULL, 13698.00);
INSERT INTO `trade` VALUES (43, 'T1549773634019659776', 1, 'test', 'PAID', '2022-07-20 23:10:02', 4899.00, NULL, 4899.00);
INSERT INTO `trade` VALUES (44, 'T1550321102432112640', 1, 'test', 'UNPAID', '2022-07-22 11:25:29', 16397.00, NULL, 16397.00);

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
