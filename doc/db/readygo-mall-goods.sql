/*
 Navicat Premium Data Transfer

 Source Server         : 49.234.85.17
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 49.234.85.17:3306
 Source Schema         : readygo-mall-goods

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 08/03/2023 15:28:50
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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, '小米', '小米公司', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1650462965036753285xiaomi.jpg', '2022-01-18 14:21:54', '2022-04-20 21:56:06', 1, 0);
INSERT INTO `brand` VALUES (2, '华为', '华为公司', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1642486939687577211huawei.jpg', '2022-01-18 14:22:21', NULL, 1, 0);
INSERT INTO `brand` VALUES (3, '苹果', '苹果笔记本，手机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1647761041160453954apple.jpg', '2022-03-20 15:24:14', NULL, 1, 0);
INSERT INTO `brand` VALUES (4, '佳能', '佳能', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1651388958250693879jianeng.jpg', '2022-05-01 15:09:20', NULL, 1, 0);
INSERT INTO `brand` VALUES (5, 'TCL', 'TCL', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1662298234099558614tcl.jpg', '2022-09-04 21:30:38', NULL, 1, 0);
INSERT INTO `brand` VALUES (6, '海尔', '海尔', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1662359150973474382haier.jpg', '2022-09-05 14:25:53', NULL, 1, 0);
INSERT INTO `brand` VALUES (7, '格力', '格力', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1662643329122287981geli.jpg', '2022-09-08 21:22:12', NULL, 1, 0);
INSERT INTO `brand` VALUES (8, '东来也', '手机壳', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/brand/logo/images/1662705533278393873dly.jpg', '2022-09-09 14:38:55', NULL, 1, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '【首降250元！指定时间点疯抢五折！】Xiaomi 12S Pro游戏徕卡拍照骁龙8+小米12spro官方旗舰店红米小米手机', '小米', 2, 8, 9, 46, 1, 1, '20229910', 1, '部', '【首降250元！指定时间点疯抢五折！】Xiaomi 12S Pro游戏徕卡拍照骁龙8+小米12spro官方旗舰店红米小米手机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662708207379263605xm1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662708210385114717xm2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662708213590954140xm3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662708216780556283xm4.jpg', '', 1, '[]', 5, '最新商品', NULL, NULL, '2022-04-10 17:46:55', '2022-09-09 15:23:49');
INSERT INTO `goods` VALUES (2, '当天发【24期免息】Mate 40E手机TD M40 5G官方旗舰店正品新款官网pro直降p50系列鸿蒙nova9 华5g为Huawei', '华为', 2, 8, 9, 46, 1, 1, '2100299331', 2, '部', '当天发【24期免息】Mate 40E手机TD M40 5G官方旗舰店正品新款官网pro直降p50系列鸿蒙nova9 华5g为Huawei', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662707968307251352hw1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662707971282442404hw2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662707974322482067hw3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662707977337254319hw4.jpg', '', 1, '[]', 5, '最新商品', NULL, NULL, '2022-04-21 17:45:27', '2022-09-09 15:21:05');
INSERT INTO `goods` VALUES (3, '佳能5d4 EOS 5D Mark IV单机身专业级单反相机24-105f4 USM全画幅', '相机，单反', 2, 11, 12, 54, 1, 1, '202205011517', 4, '台', '佳能官方专卖店,全新原装正品国行', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1651389491431421135O1CN01j2KxP61xmB4SNWvxP_!!0-item_pic.jpg_430x430q90.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/16513895119992898043.jpg', '<p>相机</p>', 1, '[]', 5, '推荐商品', NULL, NULL, '2022-05-01 15:21:50', '2022-06-04 14:46:32');
INSERT INTO `goods` VALUES (4, 'HUAWEI MateBook 14 11代英特尔酷睿处理器 16GB+512GB SSD锐炬显卡笔记本轻薄办公电脑 2K触控护眼屏', '华为', 2, 15, 16, 65, 1, 1, '202205011604', 2, '台', '华为笔记本', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1651392325988666282O1CN01N2AHiO28vIsuWewt5_!!725677994.jpg_430x430q90.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1651392330845277645O1CN01Xvvp1i1PMoMgk9fvR_!!0-item_pic.jpg_430x430q90.jpg', '<p>华为就是牛</p>', 1, '[]', 10, '最新商品,推荐商品,热卖商品', NULL, NULL, '2022-05-01 16:07:42', '2022-09-09 15:25:45');
INSERT INTO `goods` VALUES (5, 'Apple 苹果 MacBook Air 2020新款', 'apple', 4, 15, 16, 65, 1, 1, '202205151450', 3, '台', '[套餐版]Apple 苹果 MacBook Air 2020新款 8核M1芯片 8G内存 512G固态 8核图形处理器 13.3英寸笔记本电脑 轻薄本 MGNA3CH/A 银色', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1652597439343113694PFEFLnSQyU1651911291903.png', '<p>好电脑</p>', 1, '[]', 5, '最新商品,推荐商品', NULL, NULL, '2022-05-15 14:52:30', '2022-06-04 14:45:53');
INSERT INTO `goods` VALUES (6, 'TCL 55V6E 55英寸 金属全面屏 2+16GB 低蓝光护眼 双频WiFi 超薄液晶55吋平板电视机 智慧屏', '电视', 2, 1, 2, 23, 1, 1, '202209042131', 5, '台', 'TCL 55V6E 55英寸 金属全面屏 2+16GB 低蓝光护眼 双频WiFi 超薄液晶55吋平板电视机 智慧屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662357449767650389tcl1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662357454197797420tcl2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662357457503191783tcl3.jpg', '', 0, '[]', 0, '最新商品', NULL, NULL, '2022-09-04 21:33:38', '2022-10-17 14:41:18');
INSERT INTO `goods` VALUES (7, '海尔电视55R5 55英寸4K超高清智能语音网络平板家用液晶电视机', '海尔，电视', 4, 1, 2, 23, 1, 1, '202209051438', 6, '台', '海尔电视55R5 55英寸4K超高清智能语音网络平板家用液晶电视机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662360409444240981000.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662360411697106148001.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662360415041823830002.jpg', '', 1, '[]', 0, '最新商品', NULL, NULL, '2022-09-05 14:40:06', '2022-10-17 14:41:00');
INSERT INTO `goods` VALUES (8, '【新国标】格力空调大1.5p匹变频挂机冷暖家用官方旗舰店官网天丽', '格力', 2, 1, 3, 27, 1, 1, '202209082143', 7, '台', '【新国标】格力空调大1.5p匹变频挂机冷暖家用官方旗舰店官网天丽', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662644613548832606geli1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662644616942642479geli2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662644620738905574geli3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662644623715422494geli4.jpg', '', 0, '[]', 0, '最新商品,推荐商品,热卖商品', NULL, NULL, '2022-09-08 21:44:40', '2022-10-17 14:40:40');
INSERT INTO `goods` VALUES (9, '格力滚筒洗衣机全自动家用8公斤kg大容量智能变频轻音烘洗一体机', '格力', 4, 1, 4, 30, 1, 1, '202209091420', 7, '台', '格力滚筒洗衣机全自动家用8公斤kg大容量智能变频轻音烘洗一体机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704522044926109geli1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704524981669674geli2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704528864176013geli3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704531747841889geli4.jpg', '', 0, '[]', 0, '最新商品,推荐商品,特价商品,热卖商品', NULL, NULL, '2022-09-09 14:23:07', '2022-10-17 14:40:13');
INSERT INTO `goods` VALUES (10, '格力晶弘 变频冰箱 529升对开门风冷无霜-33度深冷冻BCD-529WPDC', '格力', 4, 1, 5, 35, 1, 1, '202209091425', 7, '台', '格力晶弘 变频冰箱 529升对开门风冷无霜-33度深冷冻BCD-529WPDC', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704789925958264geli1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704792647113753geli2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704795667436863geli3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662704798607120128geli4.jpg', '', 0, '[]', 5, '特价商品,热卖商品', NULL, NULL, '2022-09-09 14:27:40', '2022-10-17 14:39:51');
INSERT INTO `goods` VALUES (11, '格力（GREE）抽油烟机 塔型大油烟机大吸力/大风压/低噪油烟机', '格力', 2, 1, 6, 38, 1, 1, '202209091430', 7, '台', '格力（GREE）抽油烟机 塔型大油烟机大吸力/大风压/低噪油烟机', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705045933241285geli1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705049491984326geli2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705052660378908geli3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705056336981286geli4.jpg', '', 0, '[]', 0, '最新商品', NULL, NULL, '2022-09-09 14:31:41', '2022-10-17 14:39:02');
INSERT INTO `goods` VALUES (12, '小米米家胶囊咖啡机家用全自动小型研磨一体迷你胶囊机办公煮咖啡', '小米', 3, 1, 7, 43, 1, 1, '202209091434', 1, '台', '小米米家胶囊咖啡机家用全自动小型研磨一体迷你胶囊机办公煮咖啡', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705323526413895xm4.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705327333982989xm1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705331316706642xm2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705334723327533xm3.jpg', '', 0, '[]', 0, '最新商品,推荐商品,特价商品,热卖商品', NULL, NULL, '2022-09-09 14:36:21', '2022-10-17 14:38:42');
INSERT INTO `goods` VALUES (13, '东来也适用于华为P50Pro手机壳P50新款镜头全包防摔p50e国潮p40pro原创p40超薄p30pro创意pro+中国风保护套', '东来也', 3, 8, 10, 50, 1, 1, '202209091441', 8, '个', '东来也适用于华为P50Pro手机壳P50新款镜头全包防摔p50e国潮p40pro原创p40超薄p30pro创意pro+中国风保护套', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705689912137118dly1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705692937188314dly2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705696592693839dly3.jpg', '', 0, '[]', 0, '推荐商品,热卖商品', NULL, NULL, '2022-09-09 14:42:24', '2022-10-17 14:38:19');
INSERT INTO `goods` VALUES (14, '小米/Redmi电竞显示器 G24广色域165Hz高刷1ms响应游戏电脑显示屏', '小米', 5, 15, 17, 69, 1, 1, '202209091445', 1, '台', '小米/Redmi电竞显示器 G24广色域165Hz高刷1ms响应游戏电脑显示屏', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705971168131757xm1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705975396637815xm2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705978588453347xm3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662705983158678135xm4.jpg', '', 0, '[]', 0, '推荐商品', NULL, NULL, '2022-09-09 14:47:01', '2022-10-17 14:04:47');
INSERT INTO `goods` VALUES (15, '小米便携鼠标2无线静音鼠标办公便携家用鼠标金属滑鼠企业logo', '小米', 5, 15, 18, 74, 1, 1, '202209091450', 1, '个', '小米便携鼠标2无线静音鼠标办公便携家用鼠标金属滑鼠企业logo', 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662706258126462289xm1.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662706262353353637xm2.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662706266176251097xm3.jpg,https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/images/1662706269596574214xm4.jpg', '', 1, '[]', 0, '热卖商品', NULL, NULL, '2022-09-09 14:52:25', '2022-10-16 13:52:44');

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
) ENGINE = InnoDB AUTO_INCREMENT = 395 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `goods_category` VALUES (90, '男装', 0, 5, 4, '2022-09-07 15:04:58', NULL);
INSERT INTO `goods_category` VALUES (91, '男装', 90, 0, NULL, '2022-09-07 15:07:16', NULL);
INSERT INTO `goods_category` VALUES (92, '女装', 0, 6, 4, '2022-09-07 15:08:10', NULL);
INSERT INTO `goods_category` VALUES (93, '女装', 92, 0, NULL, '2022-09-07 15:08:24', NULL);
INSERT INTO `goods_category` VALUES (94, '童装', 0, 7, 4, '2022-09-07 15:08:51', NULL);
INSERT INTO `goods_category` VALUES (95, '童装', 94, 0, NULL, '2022-09-07 15:09:40', NULL);
INSERT INTO `goods_category` VALUES (96, '童鞋', 94, 1, NULL, '2022-09-07 15:09:51', NULL);
INSERT INTO `goods_category` VALUES (97, '内衣', 0, 8, 4, '2022-09-07 15:13:24', NULL);
INSERT INTO `goods_category` VALUES (98, '内衣', 97, 0, NULL, '2022-09-07 15:13:37', NULL);
INSERT INTO `goods_category` VALUES (99, '牛仔裤', 91, 0, NULL, '2022-09-08 11:09:20', NULL);
INSERT INTO `goods_category` VALUES (100, '休闲裤', 91, 1, NULL, '2022-09-08 11:09:31', NULL);
INSERT INTO `goods_category` VALUES (101, '衬衫', 91, 2, NULL, '2022-09-08 11:09:43', NULL);
INSERT INTO `goods_category` VALUES (102, '羽绒服', 91, 3, NULL, '2022-09-08 11:10:01', NULL);
INSERT INTO `goods_category` VALUES (103, '卫衣', 91, 4, NULL, '2022-09-08 11:10:16', NULL);
INSERT INTO `goods_category` VALUES (104, '连衣裙', 93, 0, NULL, '2022-09-08 11:10:37', NULL);
INSERT INTO `goods_category` VALUES (105, '毛衣', 93, 1, NULL, '2022-09-08 11:11:03', NULL);
INSERT INTO `goods_category` VALUES (106, '风衣', 93, 2, NULL, '2022-09-08 11:11:32', NULL);
INSERT INTO `goods_category` VALUES (107, '短裤', 93, 3, NULL, '2022-09-08 11:11:50', NULL);
INSERT INTO `goods_category` VALUES (108, '半身裙', 93, 4, NULL, '2022-09-08 11:12:09', NULL);
INSERT INTO `goods_category` VALUES (109, '卫衣', 95, 0, NULL, '2022-09-08 11:12:50', NULL);
INSERT INTO `goods_category` VALUES (110, '裤子', 95, 1, NULL, '2022-09-08 11:12:59', NULL);
INSERT INTO `goods_category` VALUES (111, '亲子装', 95, 2, NULL, '2022-09-08 11:13:12', NULL);
INSERT INTO `goods_category` VALUES (112, '袜子', 95, 2, NULL, '2022-09-08 11:13:30', NULL);
INSERT INTO `goods_category` VALUES (113, '裙子', 95, 3, NULL, '2022-09-08 11:13:44', NULL);
INSERT INTO `goods_category` VALUES (114, '运动鞋', 96, 0, NULL, '2022-09-08 11:14:01', NULL);
INSERT INTO `goods_category` VALUES (115, '靴子', 96, 1, NULL, '2022-09-08 11:14:08', NULL);
INSERT INTO `goods_category` VALUES (116, '帆布鞋', 96, 2, NULL, '2022-09-08 11:14:25', NULL);
INSERT INTO `goods_category` VALUES (117, '拖鞋', 96, 4, NULL, '2022-09-08 11:14:46', NULL);
INSERT INTO `goods_category` VALUES (118, '文胸', 98, 0, NULL, '2022-09-08 11:15:10', NULL);
INSERT INTO `goods_category` VALUES (119, '男士内裤', 98, 1, NULL, '2022-09-08 11:15:24', NULL);
INSERT INTO `goods_category` VALUES (120, '女士内裤', 98, 2, NULL, '2022-09-08 11:15:37', NULL);
INSERT INTO `goods_category` VALUES (121, '保暖内衣', 98, 3, NULL, '2022-09-08 11:15:59', NULL);
INSERT INTO `goods_category` VALUES (122, '美妆', 0, 9, 5, '2022-09-08 11:17:48', NULL);
INSERT INTO `goods_category` VALUES (123, '个护清洁', 0, 10, 5, '2022-09-08 11:18:16', NULL);
INSERT INTO `goods_category` VALUES (124, '宠物', 0, 11, 5, '2022-09-08 11:18:29', NULL);
INSERT INTO `goods_category` VALUES (125, '面部护肤', 122, 0, NULL, '2022-09-08 13:49:55', NULL);
INSERT INTO `goods_category` VALUES (126, '香水彩妆', 122, 1, NULL, '2022-09-08 13:50:08', NULL);
INSERT INTO `goods_category` VALUES (127, '男士护肤', 122, 2, NULL, '2022-09-08 13:50:28', NULL);
INSERT INTO `goods_category` VALUES (128, '洗发护发', 122, 3, NULL, '2022-09-08 13:50:38', NULL);
INSERT INTO `goods_category` VALUES (129, '口腔护理', 123, 0, NULL, '2022-09-08 13:50:54', NULL);
INSERT INTO `goods_category` VALUES (130, '身体护理', 123, 1, NULL, '2022-09-08 13:51:42', NULL);
INSERT INTO `goods_category` VALUES (131, '宠物生活', 124, 0, NULL, '2022-09-08 13:52:08', NULL);
INSERT INTO `goods_category` VALUES (132, '美白', 125, 0, NULL, '2022-09-08 13:53:45', NULL);
INSERT INTO `goods_category` VALUES (133, '防晒', 125, 1, NULL, '2022-09-08 13:53:57', NULL);
INSERT INTO `goods_category` VALUES (134, '面膜', 125, 2, NULL, '2022-09-08 13:54:08', NULL);
INSERT INTO `goods_category` VALUES (135, '洁面', 125, 3, NULL, '2022-09-08 13:54:25', NULL);
INSERT INTO `goods_category` VALUES (136, '爽肤水', 125, 4, NULL, '2022-09-08 13:54:35', NULL);
INSERT INTO `goods_category` VALUES (137, '隔离', 126, 0, NULL, '2022-09-08 13:55:11', NULL);
INSERT INTO `goods_category` VALUES (138, '粉底', 126, 1, NULL, '2022-09-08 13:55:21', NULL);
INSERT INTO `goods_category` VALUES (139, '口红', 126, 2, NULL, '2022-09-08 13:55:37', NULL);
INSERT INTO `goods_category` VALUES (140, '眼影', 126, 3, NULL, '2022-09-08 13:55:54', NULL);
INSERT INTO `goods_category` VALUES (141, '眼线', 126, 4, NULL, '2022-09-08 13:56:07', NULL);
INSERT INTO `goods_category` VALUES (142, '控油', 127, 0, NULL, '2022-09-08 13:56:32', NULL);
INSERT INTO `goods_category` VALUES (143, '洁面', 127, 1, NULL, '2022-09-08 13:56:44', NULL);
INSERT INTO `goods_category` VALUES (144, '剃须', 127, 2, NULL, '2022-09-08 13:57:02', NULL);
INSERT INTO `goods_category` VALUES (145, '防晒', 127, 3, NULL, '2022-09-08 13:57:11', NULL);
INSERT INTO `goods_category` VALUES (146, '洗发水', 128, 0, NULL, '2022-09-08 13:57:33', NULL);
INSERT INTO `goods_category` VALUES (147, '护发素', 128, 1, NULL, '2022-09-08 13:57:43', NULL);
INSERT INTO `goods_category` VALUES (148, '发膜精油', 128, 2, NULL, '2022-09-08 13:58:17', NULL);
INSERT INTO `goods_category` VALUES (149, '染发', 128, 3, NULL, '2022-09-08 13:58:28', NULL);
INSERT INTO `goods_category` VALUES (150, '牙膏', 129, 0, NULL, '2022-09-08 13:58:52', NULL);
INSERT INTO `goods_category` VALUES (151, '牙刷', 129, 1, NULL, '2022-09-08 13:59:03', NULL);
INSERT INTO `goods_category` VALUES (152, '牙线', 129, 2, NULL, '2022-09-08 13:59:12', NULL);
INSERT INTO `goods_category` VALUES (153, '漱口水', 129, 3, NULL, '2022-09-08 13:59:24', NULL);
INSERT INTO `goods_category` VALUES (154, '花露水', 130, 0, NULL, '2022-09-08 13:59:47', NULL);
INSERT INTO `goods_category` VALUES (155, '沐浴露', 130, 1, NULL, '2022-09-08 13:59:56', NULL);
INSERT INTO `goods_category` VALUES (156, '香皂', 130, 2, NULL, '2022-09-08 14:00:09', NULL);
INSERT INTO `goods_category` VALUES (157, '洗手液', 130, 3, NULL, '2022-09-08 14:00:25', NULL);
INSERT INTO `goods_category` VALUES (158, '护手霜', 130, 4, NULL, '2022-09-08 14:00:37', NULL);
INSERT INTO `goods_category` VALUES (159, '狗粮', 131, 0, NULL, '2022-09-08 14:01:04', NULL);
INSERT INTO `goods_category` VALUES (160, '狗罐头', 131, 1, NULL, '2022-09-08 14:01:16', NULL);
INSERT INTO `goods_category` VALUES (161, '猫粮', 131, 2, NULL, '2022-09-08 14:01:31', '2022-09-08 14:01:59');
INSERT INTO `goods_category` VALUES (162, '猫砂', 131, 3, NULL, '2022-09-08 14:01:45', NULL);
INSERT INTO `goods_category` VALUES (163, '宠物玩具', 131, 4, NULL, '2022-09-08 14:02:33', NULL);
INSERT INTO `goods_category` VALUES (164, '女鞋', 0, 12, 6, '2022-09-08 14:03:24', NULL);
INSERT INTO `goods_category` VALUES (165, '箱包', 0, 13, 6, '2022-09-08 14:03:47', NULL);
INSERT INTO `goods_category` VALUES (166, '钟表', 0, 14, 6, '2022-09-08 14:04:01', NULL);
INSERT INTO `goods_category` VALUES (167, '珠宝', 0, 15, 6, '2022-09-08 14:04:14', NULL);
INSERT INTO `goods_category` VALUES (168, '时尚女鞋', 164, 0, NULL, '2022-09-08 14:04:40', NULL);
INSERT INTO `goods_category` VALUES (169, '潮流女包', 165, 0, NULL, '2022-09-08 14:04:57', NULL);
INSERT INTO `goods_category` VALUES (170, '精品男包', 165, 1, NULL, '2022-09-08 14:05:09', NULL);
INSERT INTO `goods_category` VALUES (171, '钟表', 166, 0, NULL, '2022-09-08 14:06:08', NULL);
INSERT INTO `goods_category` VALUES (172, '珠宝首饰', 167, 0, NULL, '2022-09-08 14:06:24', NULL);
INSERT INTO `goods_category` VALUES (173, '休闲鞋', 168, 0, NULL, '2022-09-08 14:07:24', NULL);
INSERT INTO `goods_category` VALUES (174, '高跟鞋', 168, 1, NULL, '2022-09-08 14:07:38', NULL);
INSERT INTO `goods_category` VALUES (175, '帆布鞋', 168, 2, NULL, '2022-09-08 14:07:49', NULL);
INSERT INTO `goods_category` VALUES (176, '女靴', 168, 4, NULL, '2022-09-08 14:08:02', NULL);
INSERT INTO `goods_category` VALUES (177, '单肩包', 169, 0, NULL, '2022-09-08 14:08:23', NULL);
INSERT INTO `goods_category` VALUES (178, '手提包', 169, 1, NULL, '2022-09-08 14:08:35', NULL);
INSERT INTO `goods_category` VALUES (179, '双肩包', 169, 2, NULL, '2022-09-08 14:08:47', NULL);
INSERT INTO `goods_category` VALUES (180, '钱包', 169, 3, NULL, '2022-09-08 14:08:58', NULL);
INSERT INTO `goods_category` VALUES (181, '男士钱包', 170, 0, NULL, '2022-09-08 14:09:13', NULL);
INSERT INTO `goods_category` VALUES (182, '双肩包', 170, 1, NULL, '2022-09-08 14:09:25', NULL);
INSERT INTO `goods_category` VALUES (183, '商务公文包', 170, 2, NULL, '2022-09-08 14:09:43', NULL);
INSERT INTO `goods_category` VALUES (184, '钥匙包', 170, 3, NULL, '2022-09-08 14:09:56', NULL);
INSERT INTO `goods_category` VALUES (185, 'DW', 171, 0, NULL, '2022-09-08 14:12:14', NULL);
INSERT INTO `goods_category` VALUES (186, '浪琴', 171, 1, NULL, '2022-09-08 14:12:28', NULL);
INSERT INTO `goods_category` VALUES (187, '欧米茄', 171, 2, NULL, '2022-09-08 14:12:42', NULL);
INSERT INTO `goods_category` VALUES (188, '智能手表', 171, 3, NULL, '2022-09-08 14:13:05', NULL);
INSERT INTO `goods_category` VALUES (189, '闹钟', 171, 4, NULL, '2022-09-08 14:13:15', NULL);
INSERT INTO `goods_category` VALUES (190, '黄金', 172, 0, NULL, '2022-09-08 14:13:39', NULL);
INSERT INTO `goods_category` VALUES (191, 'K金', 172, 1, NULL, '2022-09-08 14:13:51', NULL);
INSERT INTO `goods_category` VALUES (192, '钻石', 172, 2, NULL, '2022-09-08 14:14:02', NULL);
INSERT INTO `goods_category` VALUES (193, '翡翠', 172, 3, NULL, '2022-09-08 14:14:22', NULL);
INSERT INTO `goods_category` VALUES (194, '银饰', 172, 4, NULL, '2022-09-08 14:14:35', NULL);
INSERT INTO `goods_category` VALUES (195, '男鞋', 0, 16, 7, '2022-09-08 14:15:59', NULL);
INSERT INTO `goods_category` VALUES (196, '运动', 0, 17, 7, '2022-09-08 14:16:11', NULL);
INSERT INTO `goods_category` VALUES (197, '户外', 0, 18, 7, '2022-09-08 14:16:25', NULL);
INSERT INTO `goods_category` VALUES (198, '流行男鞋', 195, 0, NULL, '2022-09-08 14:16:54', NULL);
INSERT INTO `goods_category` VALUES (199, '运动鞋包', 196, 0, NULL, '2022-09-08 14:17:30', NULL);
INSERT INTO `goods_category` VALUES (200, '运动服饰', 196, 1, NULL, '2022-09-08 14:17:43', NULL);
INSERT INTO `goods_category` VALUES (201, '健身训练', 196, 2, NULL, '2022-09-08 14:17:55', NULL);
INSERT INTO `goods_category` VALUES (202, '户外鞋服', 197, 0, NULL, '2022-09-08 14:18:14', NULL);
INSERT INTO `goods_category` VALUES (203, '垂钓用品', 197, 1, NULL, '2022-09-08 14:18:30', NULL);
INSERT INTO `goods_category` VALUES (204, '母婴', 0, 19, 8, '2022-09-08 14:19:26', NULL);
INSERT INTO `goods_category` VALUES (205, '玩具乐器', 0, 20, 8, '2022-09-08 14:19:45', NULL);
INSERT INTO `goods_category` VALUES (206, '奶粉', 204, 0, NULL, '2022-09-08 14:20:24', NULL);
INSERT INTO `goods_category` VALUES (207, '营养辅食', 204, 1, NULL, '2022-09-08 14:20:35', NULL);
INSERT INTO `goods_category` VALUES (208, '尿裤湿巾', 204, 2, NULL, '2022-09-08 14:20:51', NULL);
INSERT INTO `goods_category` VALUES (209, '喂养用品', 204, 3, NULL, '2022-09-08 14:21:13', NULL);
INSERT INTO `goods_category` VALUES (210, '童车童床', 205, 0, NULL, '2022-09-08 14:21:44', NULL);
INSERT INTO `goods_category` VALUES (211, '玩具', 205, 1, NULL, '2022-09-08 14:22:01', NULL);
INSERT INTO `goods_category` VALUES (212, '乐器', 205, 2, NULL, '2022-09-08 14:22:11', NULL);
INSERT INTO `goods_category` VALUES (213, '1段', 206, 0, NULL, '2022-09-08 14:23:16', NULL);
INSERT INTO `goods_category` VALUES (214, '2段', 206, 1, NULL, '2022-09-08 14:23:31', NULL);
INSERT INTO `goods_category` VALUES (215, '3段', 206, 2, NULL, '2022-09-08 14:23:41', NULL);
INSERT INTO `goods_category` VALUES (216, '4段', 206, 3, NULL, '2022-09-08 14:23:51', NULL);
INSERT INTO `goods_category` VALUES (217, '孕妈奶粉', 206, 4, NULL, '2022-09-08 14:24:15', NULL);
INSERT INTO `goods_category` VALUES (218, '面条粥', 207, 0, NULL, '2022-09-08 14:24:58', NULL);
INSERT INTO `goods_category` VALUES (219, '益生菌', 207, 1, NULL, '2022-09-08 14:25:11', NULL);
INSERT INTO `goods_category` VALUES (220, 'DHA', 207, 2, NULL, '2022-09-08 14:25:22', NULL);
INSERT INTO `goods_category` VALUES (221, '宝宝零食', 207, 3, NULL, '2022-09-08 14:25:38', NULL);
INSERT INTO `goods_category` VALUES (222, '拉拉裤', 208, 0, NULL, '2022-09-08 14:26:02', NULL);
INSERT INTO `goods_category` VALUES (223, '成人尿裤', 208, 1, NULL, '2022-09-08 14:26:15', NULL);
INSERT INTO `goods_category` VALUES (224, '婴儿湿巾', 208, 2, NULL, '2022-09-08 14:26:30', NULL);
INSERT INTO `goods_category` VALUES (225, '奶瓶奶嘴', 209, 0, NULL, '2022-09-08 14:26:50', NULL);
INSERT INTO `goods_category` VALUES (226, '吸奶器', 209, 1, NULL, '2022-09-08 14:27:01', NULL);
INSERT INTO `goods_category` VALUES (227, '儿童餐具', 209, 2, NULL, '2022-09-08 14:27:19', NULL);
INSERT INTO `goods_category` VALUES (228, '水壶水杯', 209, 3, NULL, '2022-09-08 14:27:38', NULL);
INSERT INTO `goods_category` VALUES (229, '安全座椅', 210, 0, NULL, '2022-09-08 14:28:13', NULL);
INSERT INTO `goods_category` VALUES (230, '婴儿推车', 210, 1, NULL, '2022-09-08 14:28:28', NULL);
INSERT INTO `goods_category` VALUES (231, '婴儿床', 210, 2, NULL, '2022-09-08 14:28:45', NULL);
INSERT INTO `goods_category` VALUES (232, '学步车', 210, 4, NULL, '2022-09-08 14:29:04', NULL);
INSERT INTO `goods_category` VALUES (233, '益智玩具', 211, 0, NULL, '2022-09-08 14:29:31', NULL);
INSERT INTO `goods_category` VALUES (234, '橡皮泥', 211, 1, NULL, '2022-09-08 14:29:42', NULL);
INSERT INTO `goods_category` VALUES (235, '毛绒玩具', 211, 2, NULL, '2022-09-08 14:29:57', NULL);
INSERT INTO `goods_category` VALUES (236, '娃娃玩具', 211, 3, NULL, '2022-09-08 14:30:10', NULL);
INSERT INTO `goods_category` VALUES (237, '钢琴', 212, 0, NULL, '2022-09-08 14:30:27', NULL);
INSERT INTO `goods_category` VALUES (238, '电子琴', 212, 1, NULL, '2022-09-08 14:30:37', NULL);
INSERT INTO `goods_category` VALUES (239, '吉他', 212, 2, NULL, '2022-09-08 14:30:49', NULL);
INSERT INTO `goods_category` VALUES (240, '电钢琴', 212, 3, NULL, '2022-09-08 14:31:00', NULL);
INSERT INTO `goods_category` VALUES (241, '食品', 0, 21, 9, '2022-09-08 14:31:52', NULL);
INSERT INTO `goods_category` VALUES (242, '酒类', 0, 22, 9, '2022-09-08 14:32:04', NULL);
INSERT INTO `goods_category` VALUES (243, '生鲜', 0, 23, 9, '2022-09-08 14:32:36', NULL);
INSERT INTO `goods_category` VALUES (244, '特产', 0, 24, 9, '2022-09-08 14:32:49', NULL);
INSERT INTO `goods_category` VALUES (245, '新鲜水果', 241, 0, NULL, '2022-09-08 14:33:34', NULL);
INSERT INTO `goods_category` VALUES (246, '精选肉类', 241, 1, NULL, '2022-09-08 14:34:10', NULL);
INSERT INTO `goods_category` VALUES (247, '中外名酒', 242, 0, NULL, '2022-09-08 14:34:33', NULL);
INSERT INTO `goods_category` VALUES (248, '海鲜水产', 243, 0, NULL, '2022-09-08 14:34:53', NULL);
INSERT INTO `goods_category` VALUES (249, '地方特产', 244, 0, NULL, '2022-09-08 14:35:23', NULL);
INSERT INTO `goods_category` VALUES (250, '茗茶', 244, 1, NULL, '2022-09-08 14:36:08', NULL);
INSERT INTO `goods_category` VALUES (251, '苹果', 245, 0, NULL, '2022-09-08 14:37:43', NULL);
INSERT INTO `goods_category` VALUES (252, '橙子', 245, 1, NULL, '2022-09-08 14:37:52', NULL);
INSERT INTO `goods_category` VALUES (253, '榴莲', 245, 2, NULL, '2022-09-08 14:38:08', NULL);
INSERT INTO `goods_category` VALUES (254, '芒果', 245, 3, NULL, '2022-09-08 14:38:18', NULL);
INSERT INTO `goods_category` VALUES (255, '猪肉', 246, 0, NULL, '2022-09-08 14:38:49', NULL);
INSERT INTO `goods_category` VALUES (256, '牛肉', 246, 1, NULL, '2022-09-08 14:38:59', NULL);
INSERT INTO `goods_category` VALUES (257, '羊肉', 246, 2, NULL, '2022-09-08 14:39:11', NULL);
INSERT INTO `goods_category` VALUES (258, '鸡肉', 246, 3, NULL, '2022-09-08 14:39:25', NULL);
INSERT INTO `goods_category` VALUES (259, '鸭肉', 246, 4, NULL, '2022-09-08 14:39:39', NULL);
INSERT INTO `goods_category` VALUES (260, '白酒', 247, 0, NULL, '2022-09-08 14:40:17', NULL);
INSERT INTO `goods_category` VALUES (261, '葡萄酒', 247, 1, NULL, '2022-09-08 14:40:27', NULL);
INSERT INTO `goods_category` VALUES (262, '洋酒', 247, 2, NULL, '2022-09-08 14:40:44', NULL);
INSERT INTO `goods_category` VALUES (263, '鱼类', 248, 0, NULL, '2022-09-08 14:41:17', NULL);
INSERT INTO `goods_category` VALUES (264, '虾类', 248, 1, NULL, '2022-09-08 14:41:27', NULL);
INSERT INTO `goods_category` VALUES (265, '蟹类', 248, 2, NULL, '2022-09-08 14:41:46', NULL);
INSERT INTO `goods_category` VALUES (266, '贝类', 248, 3, NULL, '2022-09-08 14:41:59', NULL);
INSERT INTO `goods_category` VALUES (267, '北京', 249, 0, NULL, '2022-09-08 14:42:34', NULL);
INSERT INTO `goods_category` VALUES (268, '新疆', 249, 1, NULL, '2022-09-08 14:42:48', NULL);
INSERT INTO `goods_category` VALUES (269, '陕西', 249, 2, NULL, '2022-09-08 14:43:01', NULL);
INSERT INTO `goods_category` VALUES (270, '云南', 249, 3, NULL, '2022-09-08 14:43:13', NULL);
INSERT INTO `goods_category` VALUES (271, '铁观音', 250, 0, NULL, '2022-09-08 14:43:39', NULL);
INSERT INTO `goods_category` VALUES (272, '龙井', 250, 1, NULL, '2022-09-08 14:43:51', NULL);
INSERT INTO `goods_category` VALUES (273, '乌龙茶', 250, 2, NULL, '2022-09-08 14:44:07', NULL);
INSERT INTO `goods_category` VALUES (274, '白茶', 250, 3, NULL, '2022-09-08 14:44:16', NULL);
INSERT INTO `goods_category` VALUES (275, '医药保健', 0, 25, 10, '2022-09-08 14:44:53', NULL);
INSERT INTO `goods_category` VALUES (276, '计生情趣', 0, 26, 10, '2022-09-08 14:45:09', NULL);
INSERT INTO `goods_category` VALUES (277, '中西药品', 275, 0, NULL, '2022-09-08 14:46:09', NULL);
INSERT INTO `goods_category` VALUES (278, '营养健康', 275, 1, NULL, '2022-09-08 14:46:22', NULL);
INSERT INTO `goods_category` VALUES (279, '滋补养身', 275, 2, NULL, '2022-09-08 14:46:36', NULL);
INSERT INTO `goods_category` VALUES (280, '计生情趣', 276, 0, NULL, '2022-09-08 14:47:00', NULL);
INSERT INTO `goods_category` VALUES (281, '感冒咳嗽', 277, 0, NULL, '2022-09-08 14:54:26', NULL);
INSERT INTO `goods_category` VALUES (282, '补肾壮阳', 277, 1, NULL, '2022-09-08 14:54:45', NULL);
INSERT INTO `goods_category` VALUES (283, '补气养血', 277, 2, NULL, '2022-09-08 14:54:57', NULL);
INSERT INTO `goods_category` VALUES (284, '止痛镇痛', 277, 3, NULL, '2022-09-08 14:55:14', NULL);
INSERT INTO `goods_category` VALUES (285, '增强免疫', 278, 0, NULL, '2022-09-08 14:55:41', NULL);
INSERT INTO `goods_category` VALUES (286, '骨骼健康', 278, 1, NULL, '2022-09-08 14:55:55', NULL);
INSERT INTO `goods_category` VALUES (287, '肠胃养护', 278, 2, NULL, '2022-09-08 14:56:07', NULL);
INSERT INTO `goods_category` VALUES (288, '调节三高', 278, 3, NULL, '2022-09-08 14:56:19', NULL);
INSERT INTO `goods_category` VALUES (289, '阿胶', 279, 0, NULL, '2022-09-08 14:56:58', NULL);
INSERT INTO `goods_category` VALUES (290, '枸杞', 279, 1, NULL, '2022-09-08 14:57:11', NULL);
INSERT INTO `goods_category` VALUES (291, '燕窝', 279, 2, NULL, '2022-09-08 14:57:22', NULL);
INSERT INTO `goods_category` VALUES (292, '鹿茸', 279, 3, NULL, '2022-09-08 14:57:35', NULL);
INSERT INTO `goods_category` VALUES (293, '避孕套', 280, 0, NULL, '2022-09-08 14:58:02', NULL);
INSERT INTO `goods_category` VALUES (294, '排卵验孕', 280, 1, NULL, '2022-09-08 14:58:26', NULL);
INSERT INTO `goods_category` VALUES (295, '润滑液', 280, 2, NULL, '2022-09-08 14:58:39', NULL);
INSERT INTO `goods_category` VALUES (296, '飞机杯', 280, 3, NULL, '2022-09-08 14:58:52', NULL);
INSERT INTO `goods_category` VALUES (297, '艺术', 0, 27, 11, '2022-09-08 15:00:32', NULL);
INSERT INTO `goods_category` VALUES (298, '礼品鲜花', 0, 28, 11, '2022-09-08 15:00:56', NULL);
INSERT INTO `goods_category` VALUES (299, '农资绿植', 0, 29, 11, '2022-09-08 15:01:25', NULL);
INSERT INTO `goods_category` VALUES (300, '艺术品', 297, 0, NULL, '2022-09-08 15:01:48', NULL);
INSERT INTO `goods_category` VALUES (301, '礼品', 298, 0, NULL, '2022-09-08 15:02:10', NULL);
INSERT INTO `goods_category` VALUES (302, '鲜花速递', 298, 1, NULL, '2022-09-08 15:02:52', NULL);
INSERT INTO `goods_category` VALUES (303, '绿植园艺', 299, 0, NULL, '2022-09-08 15:03:23', NULL);
INSERT INTO `goods_category` VALUES (304, '种子', 299, 1, NULL, '2022-09-08 15:03:36', NULL);
INSERT INTO `goods_category` VALUES (305, '油画', 300, 0, NULL, '2022-09-08 15:04:17', NULL);
INSERT INTO `goods_category` VALUES (306, '水墨画', 300, 1, NULL, '2022-09-08 15:04:29', NULL);
INSERT INTO `goods_category` VALUES (307, '书法', 300, 2, NULL, '2022-09-08 15:04:40', NULL);
INSERT INTO `goods_category` VALUES (308, '雕塑', 300, 3, NULL, '2022-09-08 15:04:51', NULL);
INSERT INTO `goods_category` VALUES (309, '创意礼品', 301, 0, NULL, '2022-09-08 15:05:10', NULL);
INSERT INTO `goods_category` VALUES (310, '电子礼品', 301, 1, NULL, '2022-09-08 15:05:24', NULL);
INSERT INTO `goods_category` VALUES (311, '工艺礼品', 301, 2, NULL, '2022-09-08 15:05:36', NULL);
INSERT INTO `goods_category` VALUES (312, '桌面绿植', 303, 0, NULL, '2022-09-08 15:06:06', NULL);
INSERT INTO `goods_category` VALUES (313, '绿植盆栽', 303, 1, NULL, '2022-09-08 15:06:20', NULL);
INSERT INTO `goods_category` VALUES (314, '多肉植物', 303, 2, NULL, '2022-09-08 15:06:35', NULL);
INSERT INTO `goods_category` VALUES (315, '花卉', 303, 3, NULL, '2022-09-08 15:06:51', NULL);
INSERT INTO `goods_category` VALUES (316, '花草林木类', 304, 0, NULL, '2022-09-08 15:08:04', NULL);
INSERT INTO `goods_category` VALUES (317, '瓜果类', 304, 1, NULL, '2022-09-08 15:09:18', NULL);
INSERT INTO `goods_category` VALUES (318, '大田作物类', 304, 2, NULL, '2022-09-08 15:09:34', NULL);
INSERT INTO `goods_category` VALUES (319, '图书', 0, 30, 12, '2022-09-08 15:13:20', NULL);
INSERT INTO `goods_category` VALUES (320, '文娱', 0, 31, 12, '2022-09-08 15:14:08', NULL);
INSERT INTO `goods_category` VALUES (321, '教育', 0, 32, 12, '2022-09-08 15:15:00', NULL);
INSERT INTO `goods_category` VALUES (322, '电子书', 0, 33, 12, '2022-09-08 15:15:14', NULL);
INSERT INTO `goods_category` VALUES (323, '文学', 319, 0, NULL, '2022-09-08 15:16:02', NULL);
INSERT INTO `goods_category` VALUES (324, '童书', 319, 1, NULL, '2022-09-08 15:16:15', NULL);
INSERT INTO `goods_category` VALUES (325, '教材教辅', 319, 2, NULL, '2022-09-08 15:16:34', NULL);
INSERT INTO `goods_category` VALUES (326, '文娱', 320, 0, NULL, '2022-09-08 15:17:02', NULL);
INSERT INTO `goods_category` VALUES (327, '电子书', 322, 0, NULL, '2022-09-08 15:18:07', NULL);
INSERT INTO `goods_category` VALUES (328, '人文社科', 319, 3, NULL, '2022-09-08 15:18:44', NULL);
INSERT INTO `goods_category` VALUES (329, '工业品', 0, 34, 13, '2022-09-08 15:19:44', NULL);
INSERT INTO `goods_category` VALUES (330, '工具', 329, 0, NULL, '2022-09-08 15:20:01', NULL);
INSERT INTO `goods_category` VALUES (331, '劳动防护', 329, 1, NULL, '2022-09-08 15:20:17', NULL);
INSERT INTO `goods_category` VALUES (332, '安全消防', 329, 2, NULL, '2022-09-08 15:20:33', NULL);
INSERT INTO `goods_category` VALUES (333, '仪器仪表', 329, 3, NULL, '2022-09-08 15:20:47', NULL);
INSERT INTO `goods_category` VALUES (334, '清洁用品', 329, 4, NULL, '2022-09-08 15:21:03', NULL);
INSERT INTO `goods_category` VALUES (335, '手动工具', 330, 0, NULL, '2022-09-08 15:22:51', NULL);
INSERT INTO `goods_category` VALUES (336, '电动工具', 330, 1, NULL, '2022-09-08 15:23:02', NULL);
INSERT INTO `goods_category` VALUES (337, '测量工具', 330, 2, NULL, '2022-09-08 15:23:16', NULL);
INSERT INTO `goods_category` VALUES (338, '手部防护', 331, 0, NULL, '2022-09-08 15:23:36', NULL);
INSERT INTO `goods_category` VALUES (339, '足部防护', 331, 1, NULL, '2022-09-08 15:23:48', NULL);
INSERT INTO `goods_category` VALUES (340, '身体防护', 331, 2, NULL, '2022-09-08 15:24:00', NULL);
INSERT INTO `goods_category` VALUES (341, '头部防护', 331, 3, NULL, '2022-09-08 15:24:14', NULL);
INSERT INTO `goods_category` VALUES (342, '警示标识', 332, 0, NULL, '2022-09-08 15:24:56', NULL);
INSERT INTO `goods_category` VALUES (343, '监控设备', 332, 1, NULL, '2022-09-08 15:25:08', NULL);
INSERT INTO `goods_category` VALUES (344, '安全锁具', 332, 2, NULL, '2022-09-08 15:25:19', NULL);
INSERT INTO `goods_category` VALUES (345, '消防器材', 332, 3, NULL, '2022-09-08 15:25:37', '2022-09-08 15:25:51');
INSERT INTO `goods_category` VALUES (346, '温度仪表', 333, 0, NULL, '2022-09-08 15:26:14', NULL);
INSERT INTO `goods_category` VALUES (347, '电工仪表', 333, 1, NULL, '2022-09-08 15:26:29', NULL);
INSERT INTO `goods_category` VALUES (348, '压力仪表', 333, 2, NULL, '2022-09-08 15:26:43', NULL);
INSERT INTO `goods_category` VALUES (349, '清洁工具', 334, 0, NULL, '2022-09-08 15:27:04', NULL);
INSERT INTO `goods_category` VALUES (350, '清洁设备', 334, 1, NULL, '2022-09-08 15:27:15', NULL);
INSERT INTO `goods_category` VALUES (351, '清洗保养品', 334, 2, NULL, '2022-09-08 15:27:31', NULL);
INSERT INTO `goods_category` VALUES (352, '清洗剂', 334, 3, NULL, '2022-09-08 15:27:44', NULL);
INSERT INTO `goods_category` VALUES (353, '鲜花', 302, 0, NULL, '2022-09-08 15:28:58', NULL);
INSERT INTO `goods_category` VALUES (354, '永生花', 302, 1, NULL, '2022-09-08 15:29:10', NULL);
INSERT INTO `goods_category` VALUES (355, '休闲鞋', 198, 0, NULL, '2022-09-08 15:30:43', NULL);
INSERT INTO `goods_category` VALUES (356, '正装鞋', 198, 1, NULL, '2022-09-08 15:30:59', NULL);
INSERT INTO `goods_category` VALUES (357, '拖鞋', 198, 2, NULL, '2022-09-08 15:31:13', NULL);
INSERT INTO `goods_category` VALUES (358, '跑步鞋', 199, 0, NULL, '2022-09-08 15:31:54', NULL);
INSERT INTO `goods_category` VALUES (359, '篮球鞋', 199, 1, NULL, '2022-09-08 15:32:07', NULL);
INSERT INTO `goods_category` VALUES (360, '足球鞋', 199, 2, NULL, '2022-09-08 15:32:21', NULL);
INSERT INTO `goods_category` VALUES (361, 'T恤', 200, 0, NULL, '2022-09-08 15:33:19', NULL);
INSERT INTO `goods_category` VALUES (362, '运动裤', 200, 1, NULL, '2022-09-08 15:33:30', NULL);
INSERT INTO `goods_category` VALUES (363, '健身服', 200, 2, NULL, '2022-09-08 15:33:44', NULL);
INSERT INTO `goods_category` VALUES (364, '跑步机', 201, 0, NULL, '2022-09-08 15:34:28', NULL);
INSERT INTO `goods_category` VALUES (365, '动感单车', 201, 1, NULL, '2022-09-08 15:34:40', NULL);
INSERT INTO `goods_category` VALUES (366, '椭圆机', 201, 2, NULL, '2022-09-08 15:34:51', NULL);
INSERT INTO `goods_category` VALUES (367, '哑铃', 201, 3, NULL, '2022-09-08 15:35:03', NULL);
INSERT INTO `goods_category` VALUES (368, '户外风衣', 202, 0, NULL, '2022-09-08 15:35:38', NULL);
INSERT INTO `goods_category` VALUES (369, '徒步鞋', 202, 1, NULL, '2022-09-08 15:35:52', NULL);
INSERT INTO `goods_category` VALUES (370, '登山鞋', 202, 2, NULL, '2022-09-08 15:36:05', NULL);
INSERT INTO `goods_category` VALUES (371, '钓竿', 203, 0, NULL, '2022-09-08 15:36:33', NULL);
INSERT INTO `goods_category` VALUES (372, '鱼线', 203, 1, NULL, '2022-09-08 15:36:44', NULL);
INSERT INTO `goods_category` VALUES (373, '鱼饵', 203, 2, NULL, '2022-09-08 15:37:20', NULL);
INSERT INTO `goods_category` VALUES (374, '钓鱼灯', 203, 3, NULL, '2022-09-08 15:37:34', NULL);
INSERT INTO `goods_category` VALUES (375, '小说', 323, 0, NULL, '2022-09-08 15:38:30', NULL);
INSERT INTO `goods_category` VALUES (376, '传记', 323, 1, NULL, '2022-09-08 15:38:44', NULL);
INSERT INTO `goods_category` VALUES (377, '武侠', 323, 2, NULL, '2022-09-08 15:38:58', NULL);
INSERT INTO `goods_category` VALUES (378, '0-2岁', 324, 0, NULL, '2022-09-08 15:39:25', NULL);
INSERT INTO `goods_category` VALUES (379, '3-6岁', 324, 1, NULL, '2022-09-08 15:39:38', NULL);
INSERT INTO `goods_category` VALUES (380, '7-10岁', 324, 2, NULL, '2022-09-08 15:39:50', NULL);
INSERT INTO `goods_category` VALUES (381, '儿童文学', 324, 3, NULL, '2022-09-08 15:40:03', NULL);
INSERT INTO `goods_category` VALUES (382, '教材', 325, 0, NULL, '2022-09-08 15:40:48', NULL);
INSERT INTO `goods_category` VALUES (383, '中小学教辅', 325, 1, NULL, '2022-09-08 15:41:04', NULL);
INSERT INTO `goods_category` VALUES (384, '字典词典', 325, 2, NULL, '2022-09-08 15:41:16', NULL);
INSERT INTO `goods_category` VALUES (385, '历史', 328, 0, NULL, '2022-09-08 15:41:37', NULL);
INSERT INTO `goods_category` VALUES (386, '心理学', 328, 1, NULL, '2022-09-08 15:41:51', NULL);
INSERT INTO `goods_category` VALUES (387, '政治军事', 328, 2, NULL, '2022-09-08 15:42:05', NULL);
INSERT INTO `goods_category` VALUES (388, '传统文化', 328, 3, NULL, '2022-09-08 15:42:20', NULL);
INSERT INTO `goods_category` VALUES (389, 'IP商品', 326, 0, NULL, '2022-09-08 15:42:47', NULL);
INSERT INTO `goods_category` VALUES (390, '音乐', 326, 1, NULL, '2022-09-08 15:43:41', NULL);
INSERT INTO `goods_category` VALUES (391, '游戏', 326, 2, NULL, '2022-09-08 15:43:55', NULL);
INSERT INTO `goods_category` VALUES (392, '小说', 327, 0, NULL, '2022-09-08 15:44:30', NULL);
INSERT INTO `goods_category` VALUES (393, '文学', 327, 1, NULL, '2022-09-08 15:44:40', NULL);
INSERT INTO `goods_category` VALUES (394, '经济管理', 327, 2, NULL, '2022-09-08 15:44:51', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 136 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_sku
-- ----------------------------
INSERT INTO `goods_sku` VALUES (73, 5, '2022051501', 9399.00, 8799.00, 498, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597528758451679PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (74, 5, '2022051502', 9599.00, 8999.00, 500, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597531447570304PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (75, 5, '2022051503', 10999.00, 9999.00, 398, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597533765523084PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":11,\"value\":\"8G+256G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (76, 5, '2022051504', 10889.00, 9880.00, 800, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652597536725818750PFEFLnSQyU1651911291903.png', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":11,\"value\":\"8G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":11,\"value\":\"8G+256G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (81, 3, '2022050101', 12999.00, 11999.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600178530665807RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":4,\"value\":\"4G+32G\"}]');
INSERT INTO `goods_sku` VALUES (82, 3, '2022050102', 13999.00, 12999.00, 3000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600182482789415RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":5,\"value\":\"4G+64G\"}]');
INSERT INTO `goods_sku` VALUES (83, 3, '2022050103', 11999.00, 11799.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600185377725796RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":4,\"value\":\"4G+32G\"}]');
INSERT INTO `goods_sku` VALUES (84, 3, '2022050104', 14999.00, 12999.00, 7000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600188259389140RuVkd5CO5ICXFV2QovpvaQ.png', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":4,\"value\":\"4G+32G\"},{\"id\":5,\"value\":\"4G+64G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":5,\"value\":\"4G+64G\"}]');
INSERT INTO `goods_sku` VALUES (113, 2, '2022011122', 4999.00, 3999.00, 100, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599971761640690Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (114, 2, '2022011123', 4999.00, 4888.00, 200, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599975229412779Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (115, 2, '2022011124', 6999.00, 6299.00, 500, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599978108746218Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (116, 2, '2022011125', 7999.00, 6999.00, 1000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599980695257207Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (117, 1, '2022100022', 2999.00, 2699.00, 1000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599892005820252Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (118, 1, '2022100023', 2999.00, 2799.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599895934881378Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":16,\"value\":\"黄色\"}]');
INSERT INTO `goods_sku` VALUES (119, 1, '2022100024', 2999.00, 2899.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599901133716088Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (120, 1, '2022100025', 2999.00, 2999.00, 10000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652599904162425342Qzc-AnSXiWhJKiZIejeadQ.jpg', '[{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":8,\"value\":\"6G+128G\"},{\"id\":9,\"value\":\"6G+256G\"}]},{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"},{\"id\":16,\"value\":\"黄色\"}]}]', '[{\"id\":9,\"value\":\"6G+256G\"},{\"id\":16,\"value\":\"黄色\"}]');
INSERT INTO `goods_sku` VALUES (121, 4, '2022050101', 4999.00, 4899.00, 993, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600106407320786t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":12,\"value\":\"12G+128G\"}]');
INSERT INTO `goods_sku` VALUES (122, 4, '2022050102', 5999.00, 5899.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600102441967862t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"},{\"id\":13,\"value\":\"12G+256G\"}]');
INSERT INTO `goods_sku` VALUES (123, 4, '2022050103', 6999.00, 6799.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600114335703617t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":12,\"value\":\"12G+128G\"}]');
INSERT INTO `goods_sku` VALUES (124, 4, '2022050104', 7999.00, 7599.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/1652600129160402647t-gvMHnbgVq9ZzL2HItykA.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":19,\"value\":\"蓝色\"},{\"id\":14,\"value\":\"红色\"}]},{\"id\":2,\"name\":\"内存\",\"specificationsDetailList\":[{\"id\":12,\"value\":\"12G+128G\"},{\"id\":13,\"value\":\"12G+256G\"}]}]', '[{\"id\":14,\"value\":\"红色\"},{\"id\":13,\"value\":\"12G+256G\"}]');
INSERT INTO `goods_sku` VALUES (125, 15, '202209091451', 59.00, 52.00, 1000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16658995561179078561662706258126462289xm1.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":20,\"value\":\"紫色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":20,\"value\":\"紫色\"}]');
INSERT INTO `goods_sku` VALUES (126, 15, '202209091451', 69.00, 62.00, 800, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16658995609046533491662706258126462289xm1.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":20,\"value\":\"紫色\"},{\"id\":19,\"value\":\"蓝色\"}]}]', '[{\"id\":19,\"value\":\"蓝色\"}]');
INSERT INTO `goods_sku` VALUES (127, 14, '202209091446', 799.00, 599.00, 300, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659866843565675411111.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (128, 13, '202209091442', 138.00, 69.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659886962939813931662705689912137118dly1.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (129, 12, '202209091435', 349.00, 349.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659887199958373681662705323526413895xm4.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (130, 11, '202209091432', 5399.00, 5399.00, 200, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659887401475960331662705045933241285geli1.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (131, 10, '202209091426', 4299.00, 3599.00, 5000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659887884931956311662704789925958264geli1.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (132, 9, '202209091422', 5999.00, 5999.00, 2000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659888105563184121662704522044926109geli1.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (133, 8, '202209082143', 3199.00, 2899.00, 600, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659888332878630641662644613548832606geli1.jpg', NULL, NULL);
INSERT INTO `goods_sku` VALUES (134, 7, '202209051439', 3699.00, 2799.00, 500, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16659888579589558411662360409444240981000.jpg', '[{\"id\":3,\"name\":\"颜色\",\"specificationsDetailList\":[{\"id\":14,\"value\":\"红色\"}]}]', '[{\"id\":14,\"value\":\"红色\"}]');
INSERT INTO `goods_sku` VALUES (135, 6, '202209042132', 5999.00, 5799.00, 1000, 1, 'https://dongjuntao-1303976517.cos.ap-shanghai.myqcloud.com/goods/sku/images/16622983905231978505S992rU4qs1662257189187.png', NULL, NULL);

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
