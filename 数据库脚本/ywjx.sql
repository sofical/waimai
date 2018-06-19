/*
Navicat MySQL Data Transfer

Source Server         : 182.254.148.50
Source Server Version : 50634
Source Host           : 182.254.148.50:3306
Source Database       : ywjx

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2018-06-19 11:35:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for baidu_order
-- ----------------------------
DROP TABLE IF EXISTS `baidu_order`;
CREATE TABLE `baidu_order` (
  `baidu_order_id` char(36) NOT NULL,
  `shop_id` varchar(11) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `order_info` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_sync` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `comment_id` char(36) NOT NULL DEFAULT '' COMMENT '订单ID',
  `shop_id` char(36) NOT NULL DEFAULT '' COMMENT '店铺ID',
  `order_id` char(36) DEFAULT '' COMMENT '订单id',
  `platform` tinyint(3) NOT NULL DEFAULT '0' COMMENT '平台(1饿了么,2百度,3美团)',
  `content` varchar(500) NOT NULL DEFAULT '' COMMENT '内容',
  `order_score` tinyint(3) NOT NULL DEFAULT '1' COMMENT '订单',
  `food_score` tinyint(3) NOT NULL DEFAULT '1' COMMENT '菜品评论',
  `delivery_score` tinyint(3) NOT NULL DEFAULT '1' COMMENT '快递',
  `origin_comment_id` char(36) NOT NULL DEFAULT '' COMMENT '原平台评论ID',
  `comment_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  KEY `order_time_idx` (`comment_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` char(36) NOT NULL DEFAULT '' COMMENT '订单ID',
  `phone` char(11) NOT NULL DEFAULT '0' COMMENT '手机',
  `names` char(200) NOT NULL DEFAULT '0' COMMENT '姓名',
  `address_list` text NOT NULL COMMENT '地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `dish_id` char(36) NOT NULL DEFAULT '' COMMENT '菜品ID',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '编号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `shop_id` char(36) NOT NULL DEFAULT '' COMMENT '商铺ID',
  `style` varchar(50) NOT NULL DEFAULT '' COMMENT '菜系',
  `taste` varchar(50) NOT NULL DEFAULT '' COMMENT '口味',
  `tag` varchar(50) NOT NULL DEFAULT '' COMMENT '标签',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '班级ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ele_comment
-- ----------------------------
DROP TABLE IF EXISTS `ele_comment`;
CREATE TABLE `ele_comment` (
  `ele_comment_id` char(36) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `comment_id` varchar(128) NOT NULL,
  `comment_info` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_sync` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ele_order
-- ----------------------------
DROP TABLE IF EXISTS `ele_order`;
CREATE TABLE `ele_order` (
  `ele_order_id` char(36) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `order_info` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_sync` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for ele_shop
-- ----------------------------
DROP TABLE IF EXISTS `ele_shop`;
CREATE TABLE `ele_shop` (
  `ele_shop_id` char(36) NOT NULL,
  `shop_code` varchar(255) NOT NULL,
  `access_token` varchar(255) NOT NULL,
  `token_type` varchar(255) NOT NULL,
  `expires_in` int(11) NOT NULL DEFAULT '0',
  `refresh_token` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ele_shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for meituan_comment
-- ----------------------------
DROP TABLE IF EXISTS `meituan_comment`;
CREATE TABLE `meituan_comment` (
  `meituan_comment_id` char(36) NOT NULL,
  `e_poi_id` varchar(36) NOT NULL,
  `comment_id` varchar(36) NOT NULL,
  `comment_info` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_sync` tinyint(3) DEFAULT '0' COMMENT '是同步过'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for meituan_order
-- ----------------------------
DROP TABLE IF EXISTS `meituan_order`;
CREATE TABLE `meituan_order` (
  `meituan_order_id` char(36) NOT NULL,
  `e_poi_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `order_info` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_sync` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否入库：0否，1是',
  `re_get` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否重新拉取订单：0否，1是'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for meituan_shop
-- ----------------------------
DROP TABLE IF EXISTS `meituan_shop`;
CREATE TABLE `meituan_shop` (
  `meituan_shop_id` char(36) NOT NULL DEFAULT '',
  `app_auth_token` varchar(64) DEFAULT NULL,
  `business_id` varchar(5) DEFAULT NULL,
  `e_poi_id` varchar(36) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`meituan_shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order_dish
-- ----------------------------
DROP TABLE IF EXISTS `order_dish`;
CREATE TABLE `order_dish` (
  `order_dish_id` char(36) NOT NULL DEFAULT '' COMMENT '订单ID',
  `order_id` char(36) NOT NULL DEFAULT '' COMMENT '订单',
  `dish_id` char(36) NOT NULL DEFAULT '' COMMENT '菜品',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order_whole
-- ----------------------------
DROP TABLE IF EXISTS `order_whole`;
CREATE TABLE `order_whole` (
  `order_whole_id` char(36) NOT NULL,
  `plant` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1-美团、2-饿了么、3-百度',
  `shop_id` char(64) NOT NULL,
  `order_id` char(64) NOT NULL,
  `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0交易中、1交易成功、2退单',
  `customer_name` varchar(30) NOT NULL,
  `customer_phone` varchar(15) NOT NULL,
  `customer_sex` tinyint(4) NOT NULL COMMENT '0未知1男2女',
  `original_price` float NOT NULL DEFAULT '0',
  `shipping_fee` float NOT NULL,
  `total` float NOT NULL,
  `source_order` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `idx_order_whole_plant_shop_id_order_id_1` (`plant`,`shop_id`,`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` char(36) NOT NULL DEFAULT '' COMMENT '订单ID',
  `shop_id` char(36) NOT NULL DEFAULT '' COMMENT '店铺ID',
  `customer_id` char(36) NOT NULL DEFAULT '' COMMENT '客户id',
  `platform` tinyint(3) NOT NULL DEFAULT '0' COMMENT '平台(1饿了么,2百度,3美团)',
  `origin_fee` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '原价格',
  `bonus_fee` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '优惠价格',
  `real_fee` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '实际价格',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '订单状态(0 ,1 成功,2 退单)',
  `origin_order_id` char(36) NOT NULL DEFAULT '' COMMENT '原平台订单ID',
  `order_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `customer_name` char(50) DEFAULT '',
  `customer_address` varchar(255) NOT NULL,
  `customer_phone` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`order_id`),
  KEY `order_time_idx` (`order_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` char(36) NOT NULL DEFAULT '' COMMENT '角色ID',
  `code` varchar(64) NOT NULL COMMENT '编码',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `rights` text NOT NULL COMMENT '角色权限',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `role_user_id` char(36) NOT NULL DEFAULT '' COMMENT '用户角色分配ID',
  `role_id` char(36) NOT NULL COMMENT '角色ID',
  `user_id` char(36) NOT NULL COMMENT '用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shop_id` char(36) NOT NULL DEFAULT '' COMMENT '商铺ID',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '编号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `platform` tinyint(3) NOT NULL DEFAULT '0' COMMENT '平台(1饿了么,2百度,3美团)',
  `extra` varchar(1000) NOT NULL DEFAULT '' COMMENT '拓展信息(百度:)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '班级ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` char(36) NOT NULL DEFAULT '' COMMENT '用户ID',
  `account` varchar(64) NOT NULL DEFAULT '' COMMENT '账号',
  `password` char(32) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_account_1` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
