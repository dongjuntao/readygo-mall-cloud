/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : readygo-mall-goods

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/08/2022 15:59:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌描述',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌logo',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(0) NULL DEFAULT NULL COMMENT '是否启用',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, '小米', '小米公司', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1650462965036753285xiaomi.jpg', '2022-01-18 14:21:54', '2022-04-20 21:56:06', 1, 0);
INSERT INTO `brand` VALUES (2, '华为', '华为公司', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1642486939687577211huawei.jpg', '2022-01-18 14:22:21', NULL, 1, 0);
INSERT INTO `brand` VALUES (3, '苹果', '苹果笔记本，手机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1647761041160453954apple.jpg', '2022-03-20 15:24:14', NULL, 1, 0);
INSERT INTO `brand` VALUES (4, '佳能', '佳能', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1651388958250693879jianeng.jpg', '2022-05-01 15:09:20', NULL, 1, 0);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键字',
  `admin_user_id` bigint(0) NULL DEFAULT NULL COMMENT '商户id',
  `goods_category_id_first` bigint(0) NULL DEFAULT NULL COMMENT '商品一级分类id',
  `goods_category_id_second` bigint(0) NULL DEFAULT NULL COMMENT '商品二级分类id',
  `goods_category_id_third` bigint(0) NULL DEFAULT NULL COMMENT '商品三级分类id',
  `on_sale` tinyint(0) NULL DEFAULT NULL COMMENT '是否在售',
  `freight_setting` int(0) NULL DEFAULT NULL COMMENT '运费设置',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `brand_id` bigint(0) NULL DEFAULT NULL COMMENT '所属品牌id',
  `unit` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品单位',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品简介',
  `images` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品图片',
  `info_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情',
  `specification_type` int(0) NULL DEFAULT NULL COMMENT '商品规格类型（0：单规格， 1：多规格）',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品参数',
  `points` int(0) NULL DEFAULT NULL COMMENT '商品赠送积分',
  `recommend` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品推荐',
  `weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '物流重量',
  `volume` decimal(10, 2) NULL DEFAULT NULL COMMENT '物流体积',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '小米12pro', '小米', 2, 8, 9, 46, 1, 1, '20229910', 1, '部', '测试商品一的描述', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/16496753204935295947ad15d7035c1c9a8.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/16499133397036602910b92f82917a9c400.jpg', '<p>商品详情</p>', 1, '[]', 5, '最新商品', NULL, NULL, '2022-04-10 17:46:55', '2022-06-04 14:46:53');
INSERT INTO `goods` VALUES (2, '华为mate40', '华为', 2, 8, 9, 46, 1, 1, '2100299331', 2, '部', '我是华为mate40', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1650534200705334585huawei.jpg', '<p>都是的受持读诵</p>', 1, '[]', 5, '最新商品', NULL, NULL, '2022-04-21 17:45:27', '2022-06-04 14:46:43');
INSERT INTO `goods` VALUES (3, '佳能5d4 EOS 5D Mark IV单机身专业级单反相机24-105f4 USM全画幅', '相机，单反', 2, 11, 12, 54, 1, 1, '202205011517', 4, '台', '佳能官方专卖店,全新原装正品国行', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1651389491431421135O1CN01j2KxP61xmB4SNWvxP_!!0-item_pic.jpg_430x430q90.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/16513895119992898043.jpg', '<p>相机</p>', 1, '[]', 5, '推荐商品', NULL, NULL, '2022-05-01 15:21:50', '2022-06-04 14:46:32');
INSERT INTO `goods` VALUES (4, '华为/HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', '华为', 2, 15, 16, 65, 1, 1, '202205011604', 2, '台', '华为笔记本', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1651392325988666282O1CN01N2AHiO28vIsuWewt5_!!725677994.jpg_430x430q90.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1651392330845277645O1CN01Xvvp1i1PMoMgk9fvR_!!0-item_pic.jpg_430x430q90.jpg', '<p>华为就是牛</p>', 1, '[]', 10, '最新商品,推荐商品,热卖商品', NULL, NULL, '2022-05-01 16:07:42', '2022-06-04 14:46:21');
INSERT INTO `goods` VALUES (5, 'Apple 苹果 MacBook Air 2020新款', 'apple', 4, 15, 16, 65, 1, 1, '202205151450', 3, '台', '[套餐版]Apple 苹果 MacBook Air 2020新款 8核M1芯片 8G内存 512G固态 8核图形处理器 13.3英寸笔记本电脑 轻薄本 MGNA3CH/A 银色', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1652597439343113694PFEFLnSQyU1651911291903.png', '<p>好电脑</p>', 1, '[]', 5, '最新商品,推荐商品', NULL, NULL, '2022-05-15 14:52:30', '2022-06-04 14:45:53');

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品分类名称',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父id',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序',
  `group_num` int(0) NULL DEFAULT NULL COMMENT '分类分组，同一个组的分类在商城前台导航展示在一起',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES (1, '家用电器', 0, 0, 1, '2022-04-07 18:58:21', '2022-04-08 16:45:15');
INSERT INTO `goods_category` VALUES (2, '电视', 1, 0, NULL, '2022-04-07 18:58:38', NULL);
INSERT INTO `goods_category` VALUES (3, '空调', 1, 1, NULL, '2022-04-07 18:58:46', NULL);
INSERT INTO `goods_category` VALUES (4, '洗衣机', 1, 2, NULL, '2022-04-07 18:58:58', NULL);
INSERT INTO `goods_category` VALUES (5, '冰箱', 1, 3, NULL, '2022-04-07 18:59:13', NULL);
INSERT INTO `goods_category` VALUES (6, '厨卫大电', 1, 4, NULL, '2022-04-07 18:59:40', NULL);
INSERT INTO `goods_category` VALUES (7, '厨卫小电', 1, 5, NULL, '2022-04-07 18:59:58', NULL);
INSERT INTO `goods_category` VALUES (8, '手机', 0, 1, 2, '2022-04-07 19:00:23', '2022-04-08 16:45:23');
INSERT INTO `goods_category` VALUES (9, '手机通讯', 8, 0, NULL, '2022-04-07 19:00:40', NULL);
INSERT INTO `goods_category` VALUES (10, '手机配件', 8, 1, NULL, '2022-04-07 19:01:44', NULL);
INSERT INTO `goods_category` VALUES (11, '数码', 0, 2, 2, '2022-04-07 19:02:00', '2022-04-08 16:45:30');
INSERT INTO `goods_category` VALUES (12, '摄像摄像', 11, 0, NULL, '2022-04-07 19:02:23', NULL);
INSERT INTO `goods_category` VALUES (13, '数码配件', 11, 1, NULL, '2022-04-07 19:02:35', NULL);
INSERT INTO `goods_category` VALUES (14, '智能设备', 11, 2, NULL, '2022-04-07 19:02:50', NULL);
INSERT INTO `goods_category` VALUES (15, '电脑', 0, 3, 3, '2022-04-07 19:05:05', '2022-04-08 16:45:36');
INSERT INTO `goods_category` VALUES (16, '电脑整机', 15, 0, NULL, '2022-04-07 19:05:33', NULL);
INSERT INTO `goods_category` VALUES (17, '电脑配件', 15, 1, NULL, '2022-04-07 19:05:49', NULL);
INSERT INTO `goods_category` VALUES (18, '外设产品', 15, 2, NULL, '2022-04-07 19:06:08', NULL);
INSERT INTO `goods_category` VALUES (19, '办公', 0, 4, 3, '2022-04-07 19:07:03', '2022-04-08 16:45:49');
INSERT INTO `goods_category` VALUES (20, '办公设备', 19, 0, NULL, '2022-04-07 19:07:20', NULL);
INSERT INTO `goods_category` VALUES (21, '文具', 19, 1, NULL, '2022-04-07 19:07:30', NULL);
INSERT INTO `goods_category` VALUES (22, '耗材', 19, 2, NULL, '2022-04-07 19:07:40', NULL);
INSERT INTO `goods_category` VALUES (23, '全面屏电视', 2, 0, NULL, '2022-04-07 19:10:00', NULL);
INSERT INTO `goods_category` VALUES (24, '教育电视', 2, 1, NULL, '2022-04-07 19:10:11', NULL);
INSERT INTO `goods_category` VALUES (25, '智慧屏', 2, 2, NULL, '2022-04-07 19:10:27', NULL);
INSERT INTO `goods_category` VALUES (26, 'OLED电视', 2, 3, NULL, '2022-04-07 19:10:41', NULL);
INSERT INTO `goods_category` VALUES (27, '变频空调', 3, 0, NULL, '2022-04-07 19:11:29', NULL);
INSERT INTO `goods_category` VALUES (28, '中央空调', 3, 1, NULL, '2022-04-07 19:11:45', NULL);
INSERT INTO `goods_category` VALUES (29, '移动空调', 3, 2, NULL, '2022-04-07 19:12:00', NULL);
INSERT INTO `goods_category` VALUES (30, '滚筒洗衣机', 4, 0, NULL, '2022-04-07 19:12:47', NULL);
INSERT INTO `goods_category` VALUES (31, '波轮洗衣机', 4, 1, NULL, '2022-04-07 19:13:24', NULL);
INSERT INTO `goods_category` VALUES (32, '迷你洗衣机', 4, 2, NULL, '2022-04-07 19:13:43', NULL);
INSERT INTO `goods_category` VALUES (33, '烘干机', 4, 3, NULL, '2022-04-07 19:14:01', NULL);
INSERT INTO `goods_category` VALUES (34, '多门', 5, 0, NULL, '2022-04-07 19:14:24', NULL);
INSERT INTO `goods_category` VALUES (35, '对开门', 5, 0, NULL, '2022-04-07 19:14:29', NULL);
INSERT INTO `goods_category` VALUES (36, '三门', 5, 0, NULL, '2022-04-07 19:14:35', NULL);
INSERT INTO `goods_category` VALUES (37, '双门', 5, 0, NULL, '2022-04-07 19:14:45', NULL);
INSERT INTO `goods_category` VALUES (38, '油烟机', 6, 0, NULL, '2022-04-07 19:15:38', NULL);
INSERT INTO `goods_category` VALUES (39, '燃气灶', 6, 1, NULL, '2022-04-07 19:16:00', NULL);
INSERT INTO `goods_category` VALUES (40, '洗碗机', 6, 2, NULL, '2022-04-07 19:16:13', NULL);
INSERT INTO `goods_category` VALUES (41, '太阳能热水器', 6, 3, NULL, '2022-04-07 19:17:49', NULL);
INSERT INTO `goods_category` VALUES (42, '破壁机', 7, 0, NULL, '2022-04-07 19:18:09', NULL);
INSERT INTO `goods_category` VALUES (43, '咖啡机', 7, 1, NULL, '2022-04-07 19:18:20', NULL);
INSERT INTO `goods_category` VALUES (44, '榨汁机', 7, 2, NULL, '2022-04-07 19:18:27', NULL);
INSERT INTO `goods_category` VALUES (45, '电饼铛', 7, 3, NULL, '2022-04-07 19:18:53', NULL);
INSERT INTO `goods_category` VALUES (46, '手机', 9, 0, NULL, '2022-04-07 19:21:09', NULL);
INSERT INTO `goods_category` VALUES (47, '游戏手机', 9, 1, NULL, '2022-04-07 19:21:16', NULL);
INSERT INTO `goods_category` VALUES (48, '拍照手机', 9, 2, NULL, '2022-04-07 19:21:40', NULL);
INSERT INTO `goods_category` VALUES (49, '全面屏手机', 9, 3, NULL, '2022-04-07 19:21:51', '2022-04-07 19:21:57');
INSERT INTO `goods_category` VALUES (50, '手机壳', 10, 0, NULL, '2022-04-07 19:22:58', NULL);
INSERT INTO `goods_category` VALUES (51, '贴膜', 10, 1, NULL, '2022-04-07 19:23:10', NULL);
INSERT INTO `goods_category` VALUES (52, '充电器', 10, 2, NULL, '2022-04-07 19:23:20', NULL);
INSERT INTO `goods_category` VALUES (53, '数据线', 10, 3, NULL, '2022-04-07 19:23:37', NULL);
INSERT INTO `goods_category` VALUES (54, '数码相机', 12, 0, NULL, '2022-04-07 19:24:06', NULL);
INSERT INTO `goods_category` VALUES (55, '单反相机', 12, 1, NULL, '2022-04-07 19:24:16', NULL);
INSERT INTO `goods_category` VALUES (56, '摄像机', 12, 2, NULL, '2022-04-07 19:24:35', NULL);
INSERT INTO `goods_category` VALUES (57, '存储卡', 13, 0, NULL, '2022-04-07 19:24:53', NULL);
INSERT INTO `goods_category` VALUES (58, '相机包', 13, 1, NULL, '2022-04-07 19:25:13', NULL);
INSERT INTO `goods_category` VALUES (59, '滤镜', 13, 2, NULL, '2022-04-07 19:25:27', NULL);
INSERT INTO `goods_category` VALUES (60, '支架', 13, 3, NULL, '2022-04-07 19:25:52', NULL);
INSERT INTO `goods_category` VALUES (61, '智能手表', 14, 0, NULL, '2022-04-07 19:27:01', NULL);
INSERT INTO `goods_category` VALUES (62, '智能手环', 14, 1, NULL, '2022-04-07 19:27:10', NULL);
INSERT INTO `goods_category` VALUES (63, '智能眼镜', 14, 2, NULL, '2022-04-07 19:27:41', NULL);
INSERT INTO `goods_category` VALUES (64, '智能机器人', 14, 3, NULL, '2022-04-07 19:28:02', NULL);
INSERT INTO `goods_category` VALUES (65, '笔记本', 16, 0, NULL, '2022-04-07 19:28:23', NULL);
INSERT INTO `goods_category` VALUES (66, '游戏本', 16, 1, NULL, '2022-04-07 19:28:32', NULL);
INSERT INTO `goods_category` VALUES (67, '平板电脑', 16, 2, NULL, '2022-04-07 19:28:47', NULL);
INSERT INTO `goods_category` VALUES (68, '台式机', 16, 3, NULL, '2022-04-07 19:28:59', NULL);
INSERT INTO `goods_category` VALUES (69, '显示器', 17, 0, NULL, '2022-04-07 19:29:16', NULL);
INSERT INTO `goods_category` VALUES (70, 'CPU', 17, 1, NULL, '2022-04-07 19:29:23', NULL);
INSERT INTO `goods_category` VALUES (71, '主板', 17, 2, NULL, '2022-04-07 19:29:40', NULL);
INSERT INTO `goods_category` VALUES (72, '显卡', 17, 3, NULL, '2022-04-07 19:29:47', NULL);
INSERT INTO `goods_category` VALUES (73, '硬盘', 17, 4, NULL, '2022-04-07 19:30:39', NULL);
INSERT INTO `goods_category` VALUES (74, '鼠标', 18, 0, NULL, '2022-04-07 19:30:55', NULL);
INSERT INTO `goods_category` VALUES (75, '键盘', 18, 1, NULL, '2022-04-07 19:31:28', NULL);
INSERT INTO `goods_category` VALUES (76, '键鼠套装', 18, 2, NULL, '2022-04-07 19:31:52', NULL);
INSERT INTO `goods_category` VALUES (77, 'U盘', 18, 3, NULL, '2022-04-07 19:32:03', NULL);
INSERT INTO `goods_category` VALUES (78, '投影机', 20, 0, NULL, '2022-04-07 19:32:43', NULL);
INSERT INTO `goods_category` VALUES (79, '打印机', 20, 1, NULL, '2022-04-07 19:32:50', NULL);
INSERT INTO `goods_category` VALUES (80, '传真设备', 20, 2, NULL, '2022-04-07 19:33:25', NULL);
INSERT INTO `goods_category` VALUES (81, '碎纸机', 20, 3, NULL, '2022-04-07 19:33:31', NULL);
INSERT INTO `goods_category` VALUES (82, '笔类', 21, 0, NULL, '2022-04-07 19:33:52', NULL);
INSERT INTO `goods_category` VALUES (83, '办公文具', 21, 1, NULL, '2022-04-07 19:34:06', NULL);
INSERT INTO `goods_category` VALUES (84, '文件收纳', 21, 2, NULL, '2022-04-07 19:34:24', NULL);
INSERT INTO `goods_category` VALUES (85, '计算器', 21, 3, NULL, '2022-04-07 19:34:38', NULL);
INSERT INTO `goods_category` VALUES (86, '墨盒', 22, 0, NULL, '2022-04-07 19:34:58', NULL);
INSERT INTO `goods_category` VALUES (87, '色带', 22, 1, NULL, '2022-04-07 19:35:12', NULL);
INSERT INTO `goods_category` VALUES (88, '纸类', 22, 2, NULL, '2022-04-07 19:35:22', NULL);
INSERT INTO `goods_category` VALUES (89, '刻录光盘', 22, 3, NULL, '2022-04-07 19:35:35', NULL);

-- ----------------------------
-- Table structure for goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `goods_sku`;
CREATE TABLE `goods_sku`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku编码',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '原价（元）',
  `selling_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '销售价（元）',
  `stock` int(0) NULL DEFAULT NULL COMMENT '库存',
  `enable` tinyint(0) NULL DEFAULT NULL COMMENT '是否启用',
  `image` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku图片',
  `extend_attr` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品sku扩展属性',
  `extend_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品sku扩展属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_sku
-- ----------------------------
INSERT INTO `goods_sku` VALUES (73, 5, '2022051501', 9399.00, 8799.00, 498, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597528758451679PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (74, 5, '2022051502', 9599.00, 8999.00, 500, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597531447570304PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (75, 5, '2022051503', 10999.00, 9999.00, 398, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597533765523084PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":11,\"value\":\"8G+256G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (76, 5, '2022051504', 10889.00, 9880.00, 800, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597536725818750PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":11,\"value\":\"8G+256G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (77, 4, '2022050101', 4999.00, 4899.00, 993, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600106407320786t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":12,\"value\":\"12G+128G\"}]');
INSERT INTO `goods_sku` VALUES (78, 4, '2022050102', 5999.00, 5899.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600102441967862t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":13,\"value\":\"12G+256G\"}]');
INSERT INTO `goods_sku` VALUES (79, 4, '2022050103', 6999.00, 6799.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600114335703617t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":12,\"value\":\"12G+128G\"}]');
INSERT INTO `goods_sku` VALUES (80, 4, '2022050104', 7999.00, 7599.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600129160402647t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":13,\"value\":\"12G+256G\"}]');
INSERT INTO `goods_sku` VALUES (81, 3, '2022050101', 12999.00, 11999.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600178530665807RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":4,\"value\":\"4G+32G\"}]');
INSERT INTO `goods_sku` VALUES (82, 3, '2022050102', 13999.00, 12999.00, 3000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600182482789415RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":5,\"value\":\"4G+64G\"}]');
INSERT INTO `goods_sku` VALUES (83, 3, '2022050103', 11999.00, 11799.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600185377725796RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":4,\"value\":\"4G+32G\"}]');
INSERT INTO `goods_sku` VALUES (84, 3, '2022050104', 14999.00, 12999.00, 7000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600188259389140RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":5,\"value\":\"4G+64G\"}]');
INSERT INTO `goods_sku` VALUES (85, 2, '2022011122', 4999.00, 4998.00, 100, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599971761640690Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (86, 2, '2022011123', 4999.00, 4888.00, 200, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599975229412779Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (87, 2, '2022011124', 4999.00, 4799.00, 500, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599978108746218Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (88, 2, '2022011125', 4999.00, 4599.00, 1000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599980695257207Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (89, 1, '2022100022', 2999.00, 2699.00, 1000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599892005820252Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (90, 1, '2022100023', 2999.00, 2799.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599895934881378Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":16,\"value\":\"黄色\"}]');
INSERT INTO `goods_sku` VALUES (91, 1, '2022100024', 2999.00, 2899.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599901133716088Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (92, 1, '2022100025', 2999.00, 2999.00, 10000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599904162425342Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":16,\"value\":\"黄色\"}]');

-- ----------------------------
-- Table structure for goods_specifications
-- ----------------------------
DROP TABLE IF EXISTS `goods_specifications`;
CREATE TABLE `goods_specifications`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格描述',
  `admin_user_id` bigint(0) NULL DEFAULT NULL COMMENT '所属商户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_specifications
-- ----------------------------
INSERT INTO `goods_specifications` VALUES (1, '尺码', '尺码大小', 2, '2022-01-18 14:23:02', NULL);
INSERT INTO `goods_specifications` VALUES (2, '内存', '内存+闪存', 2, '2022-01-18 14:39:01', NULL);
INSERT INTO `goods_specifications` VALUES (3, '颜色', '颜色选择', 2, '2022-01-18 14:41:00', NULL);

-- ----------------------------
-- Table structure for goods_specifications_detail
-- ----------------------------
DROP TABLE IF EXISTS `goods_specifications_detail`;
CREATE TABLE `goods_specifications_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_specifications_id` bigint(0) NULL DEFAULT NULL COMMENT '规格id',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格值',
  `enable` tinyint(0) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_specifications_detail
-- ----------------------------
INSERT INTO `goods_specifications_detail` VALUES (1, 1, 'L', 1);
INSERT INTO `goods_specifications_detail` VALUES (2, 1, 'M', 1);
INSERT INTO `goods_specifications_detail` VALUES (3, 1, 'S', 1);
INSERT INTO `goods_specifications_detail` VALUES (4, 2, '4G+32G', 1);
INSERT INTO `goods_specifications_detail` VALUES (5, 2, '4G+64G', 1);
INSERT INTO `goods_specifications_detail` VALUES (6, 2, '4G+128G', 1);
INSERT INTO `goods_specifications_detail` VALUES (7, 2, '6G+64G', 1);
INSERT INTO `goods_specifications_detail` VALUES (8, 2, '6G+128G', 1);
INSERT INTO `goods_specifications_detail` VALUES (9, 2, '6G+256G', 1);
INSERT INTO `goods_specifications_detail` VALUES (10, 2, '8G+128G', 1);
INSERT INTO `goods_specifications_detail` VALUES (11, 2, '8G+256G', 1);
INSERT INTO `goods_specifications_detail` VALUES (12, 2, '12G+128G', 1);
INSERT INTO `goods_specifications_detail` VALUES (13, 2, '12G+256G', 1);
INSERT INTO `goods_specifications_detail` VALUES (14, 3, '红色', 1);
INSERT INTO `goods_specifications_detail` VALUES (15, 3, '橙色', 1);
INSERT INTO `goods_specifications_detail` VALUES (16, 3, '黄色', 1);
INSERT INTO `goods_specifications_detail` VALUES (17, 3, '绿色', 1);
INSERT INTO `goods_specifications_detail` VALUES (18, 3, '青色', 1);
INSERT INTO `goods_specifications_detail` VALUES (19, 3, '蓝色', 1);
INSERT INTO `goods_specifications_detail` VALUES (20, 3, '紫色', 1);

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
