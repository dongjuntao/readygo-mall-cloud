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

 Date: 30/08/2023 15:50:48
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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `sub_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子订单状态',
  `sub_tracking_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子订单物流单号（拆分订单时，可根据子物流单号跟踪物流信息）',
  `sub_logistics_company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子订单物流公司',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '子订单创建时间',
  `receiving_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (41, 28, 'O1689182607700529152', 'S1689182608002519040', 15, 125, '小米便携鼠标2无线静音鼠标办公便携家用鼠标金属滑鼠企业logo', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16658995561179078561662706258126462289xm1.jpg', 52.00, 1, '52', 'NOT_APPLIED', 'NOT_COMMENTED', 'FINISHED', '43233232', '德邦物流股份有限公司', '2023-08-09 15:51:32', '2023-08-09 15:54:26');
INSERT INTO `order_detail` VALUES (42, 28, 'O1689182607700529152', 'S1689182608002519041', 14, 127, '小米/Redmi电竞显示器 G24广色域165Hz高刷1ms响应游戏电脑显示屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659866843565675411111.jpg', 599.00, 1, '599', 'NOT_APPLIED', 'NOT_COMMENTED', 'FINISHED', '43233232', '德邦物流股份有限公司', '2023-08-09 15:51:32', '2023-08-09 15:54:11');

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
  `recipient_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人姓名',
  `recipient_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人详细地址',
  `recipient_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人手机号',
  `region_names` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地区名称（省、市、区县）如（安徽省 淮南市 寿县）',
  `is_split` tinyint(1) NULL DEFAULT NULL COMMENT '是否拆分订单发货',
  `tracking_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流单号（未拆分订单时，可根据物流单号跟踪物流信息）',
  `logistics_company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '下单时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '订单更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (28, 'O1689182607700529152', 47, 'T1689182607453065216', 1, 'test', 5, '平台自营商户2', 0, 'FINISHED', NULL, 'ALIPAY', '2023-08-09 15:52:40', NULL, 651.00, 0.00, 651.00, '董俊涛', '航头镇鹤沙航城东茗苑', '18888888881', '上海市 上海市 浦东新区', 0, '43233232', '德邦物流股份有限公司', '2023-08-09 15:51:32', '2023-08-09 15:54:03');

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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `order_invoice` VALUES (9, 9, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (10, 10, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (11, 11, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (12, 12, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (13, 13, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (14, 14, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (15, 15, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (16, 16, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (17, 17, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (18, 18, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (19, 19, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (20, 20, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (21, 21, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (22, 22, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (23, 23, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (24, 24, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (25, 25, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (26, 26, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (27, 27, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (28, 28, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');
INSERT INTO `order_invoice` VALUES (29, 29, '1', '1', '董俊涛', '333555667000111223', 1, '15720009928', '15720009928@163.com');

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
INSERT INTO `recipient_info_selected` VALUES (3, 1, 2);

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易号',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员id(买家id)',
  `member_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员名称(买家名称)',
  `trade_time` datetime(0) NULL DEFAULT NULL COMMENT '交易时间',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品总金额',
  `freight` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `final_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '最终金额（应付金额）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trade
-- ----------------------------
INSERT INTO `trade` VALUES (1, 'T1663741467815972864', 1, 'test', '2023-05-31 10:57:31', 349.00, NULL, 349.00);
INSERT INTO `trade` VALUES (2, 'T1663741687232598016', 1, 'test', '2023-05-31 10:58:24', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (3, 'T1668510879668375552', 1, 'test', '2023-06-13 14:49:28', 349.00, NULL, 349.00);
INSERT INTO `trade` VALUES (4, 'T1678767238158618624', 1, 'test', '2023-07-11 22:04:34', 2699.00, NULL, 2699.00);
INSERT INTO `trade` VALUES (5, 'T1678767768125706240', 1, 'test', '2023-07-11 22:06:41', 89.00, NULL, 89.00);
INSERT INTO `trade` VALUES (6, 'T1678768724322160640', 1, 'test', '2023-07-11 22:10:29', 39.90, NULL, 39.90);
INSERT INTO `trade` VALUES (7, 'T1678769063976898560', 1, 'test', '2023-07-11 22:11:50', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (8, 'T1678769120788746240', 1, 'test', '2023-07-11 22:12:03', 3999.00, NULL, 3999.00);
INSERT INTO `trade` VALUES (9, 'T1679760547622555648', 1, 'test', '2023-07-14 15:51:38', 599.00, NULL, 599.00);
INSERT INTO `trade` VALUES (10, 'T1679761081767170048', 1, 'test', '2023-07-14 15:53:45', 52.00, NULL, 52.00);
INSERT INTO `trade` VALUES (11, 'T1679762547709972480', 1, 'test', '2023-07-14 15:59:35', 3999.00, NULL, 3999.00);
INSERT INTO `trade` VALUES (12, 'T1679763727580270592', 1, 'test', '2023-07-14 16:04:16', 2699.00, NULL, 2699.00);
INSERT INTO `trade` VALUES (13, 'T1679764929009291264', 1, 'test', '2023-07-14 16:09:02', 2699.00, NULL, 2699.00);
INSERT INTO `trade` VALUES (14, 'T1679765639717326848', 1, 'test', '2023-07-14 16:11:52', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (15, 'T1679767977542684672', 1, 'test', '2023-07-14 16:21:09', 52.00, NULL, 52.00);
INSERT INTO `trade` VALUES (16, 'T1679769634645086208', 1, 'test', '2023-07-14 16:27:44', 4899.00, NULL, 4899.00);
INSERT INTO `trade` VALUES (17, 'T1680814224374370304', 1, 'test', '2023-07-17 13:38:34', 8799.00, NULL, 8799.00);
INSERT INTO `trade` VALUES (18, 'T1680815192092577792', 1, 'test', '2023-07-17 13:42:24', 599.00, NULL, 599.00);
INSERT INTO `trade` VALUES (19, 'T1680816784397176832', 1, 'test', '2023-07-17 13:48:44', 8799.00, NULL, 8799.00);
INSERT INTO `trade` VALUES (20, 'T1680819803926958080', 1, 'test', '2023-07-17 14:00:44', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (21, 'T1680840731645841408', 1, 'test', '2023-07-17 15:23:54', 3599.00, NULL, 3599.00);
INSERT INTO `trade` VALUES (22, 'T1680858833137831936', 1, 'test', '2023-07-17 16:35:49', 651.00, NULL, 641.00);
INSERT INTO `trade` VALUES (23, 'T1686290190286589952', 1, 'test', '2023-08-01 16:18:06', 349.00, NULL, 349.00);
INSERT INTO `trade` VALUES (24, 'T1686303215504723968', 1, 'test', '2023-08-01 17:09:51', 69.00, NULL, 69.00);
INSERT INTO `trade` VALUES (25, 'T1687020397058461696', 1, 'test', '2023-08-03 16:39:41', 2768.00, NULL, 2768.00);
INSERT INTO `trade` VALUES (26, 'T1687022651303923712', 1, 'test', '2023-08-03 16:48:38', 5498.00, NULL, 5498.00);
INSERT INTO `trade` VALUES (27, 'T1687023960920821760', 1, 'test', '2023-08-03 16:53:50', 13698.00, NULL, 13698.00);
INSERT INTO `trade` VALUES (28, 'T1687024276714164224', 1, 'test', '2023-08-03 16:55:06', 13698.00, NULL, 13698.00);
INSERT INTO `trade` VALUES (29, 'T1687029701429825536', 1, 'test', '2023-08-03 17:16:39', 13698.00, NULL, 13698.00);
INSERT INTO `trade` VALUES (30, 'T1687030457658642432', 1, 'test', '2023-08-03 17:19:39', 8298.00, NULL, 8298.00);
INSERT INTO `trade` VALUES (31, 'T1687033670143184896', 1, 'test', '2023-08-03 17:32:25', 6698.00, NULL, 6698.00);
INSERT INTO `trade` VALUES (32, 'T1687280958660808704', 1, 'test', '2023-08-04 09:55:03', 6698.00, NULL, 6698.00);
INSERT INTO `trade` VALUES (33, 'T1687298071727312896', 1, 'test', '2023-08-04 11:03:03', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (34, 'T1687298820410576896', 1, 'test', '2023-08-04 11:06:02', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (35, 'T1687300080694071296', 1, 'test', '2023-08-04 11:11:02', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (36, 'T1687386045798289408', 1, 'test', '2023-08-04 16:52:38', 3999.00, NULL, 3999.00);
INSERT INTO `trade` VALUES (37, 'T1688753020520763392', 1, 'test', '2023-08-08 11:24:30', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (38, 'T1688753682390323200', 1, 'test', '2023-08-08 11:27:08', 0.00, NULL, 0.00);
INSERT INTO `trade` VALUES (39, 'T1688754197161447424', 1, 'test', '2023-08-08 11:29:11', 3999.00, NULL, 3999.00);
INSERT INTO `trade` VALUES (40, 'T1688819155177967616', 1, 'test', '2023-08-08 15:47:18', 2899.00, NULL, 2899.00);
INSERT INTO `trade` VALUES (41, 'T1688823262605348864', 1, 'test', '2023-08-08 16:03:37', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (42, 'T1688825403982090240', 1, 'test', '2023-08-08 16:12:08', 6698.00, NULL, 6698.00);
INSERT INTO `trade` VALUES (43, 'T1688832255251845120', 1, 'test', '2023-08-08 16:39:21', 6698.00, NULL, 6698.00);
INSERT INTO `trade` VALUES (44, 'T1689118995741020160', 1, 'test', '2023-08-09 11:38:46', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (45, 'T1689174357290651648', 1, 'test', '2023-08-09 15:18:45', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (46, 'T1689178692351299584', 1, 'test', '2023-08-09 15:35:58', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (47, 'T1689182607453065216', 1, 'test', '2023-08-09 15:51:32', 651.00, NULL, 651.00);
INSERT INTO `trade` VALUES (48, 'T1689192500666961920', 1, 'test', '2023-08-09 16:30:50', 69.00, NULL, 69.00);

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
