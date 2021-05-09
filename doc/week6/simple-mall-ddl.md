题：基于电商交易场景（用户、商品、订单），设计一套简单的表结构
参考：https://github.com/macrozheng/mall
# 表公共字段
version
trace_id
create_time
modify_time
server
client
# 基本信息
## 地址树 address

# 用户域
## 客户基本信息 customer
```
-- DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
`phone` varchar(32) DEFAULT NULL COMMENT '手机号',
`mail` varchar(120) DEFAULT NULL COMMENT '邮箱',
`nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
`head` varchar(400) DEFAULT NULL COMMENT '头像URL',
`birthday` varchar(32) DEFAULT NULL COMMENT '生日',
`sex` tinyint(2) DEFAULT '0' COMMENT '性别 0:未知，1:男，2:女',
`state` tinyint(4) DEFAULT '1' COMMENT '用户状态| 0:禁用， 1:启用',
`role` varchar(16) NOT NULL DEFAULT 'USER' COMMENT '角色:ADMIN, USER, ANONYMOUS',
`register_channel` bigint(20) DEFAULT NULL COMMENT '注册来源',
`online_no` int(11) DEFAULT '1' COMMENT '在线数量',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
`server` varchar(20) DEFAULT NULL COMMENT 'server信息',
`client` varchar(20) DEFAULT NULL COMMENT 'client信息',
`version` int(10) NOT NULL,
PRIMARY KEY (`id`),
KEY `idx_phone` (`phone`),
KEY `idx_mail` (`mail`),
KEY `idx_nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息表';
```
## 用户基本信息 user
```
-- DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
`fk_customer_id` bigint(20) NOT NULL COMMENT '客户标识',
`fk_device_id`  int(11) DEFAULT NULL COMMENT '设备标识',
`device_type` tinyint(4) DEFAULT NULL COMMENT '设备类型',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
`server` varchar(20) DEFAULT NULL COMMENT 'server信息',
`client` varchar(20) DEFAULT NULL COMMENT 'client信息',
`version` int(10) NOT NULL,
PRIMARY KEY (`id`),
KEY `idx_customer_id` (`fk_customer_id`),
KEY `idx_device_id` (`fk_device_id`),
KEY `idx_device_type` (`device_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';
```

## 用户认证信息表 user_auth
```
-- DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth` (
`user_id` bigint(20) NOT NULL DEFAULT '0',
`fk_customer_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '客户标识',
`user_name` varchar(255) NOT NULL COMMENT '用户名',
`password` varchar(255) DEFAULT NULL COMMENT '密码',
`type` tinyint(4) DEFAULT NULL COMMENT '注册类型, 1:手机号注册，2:邮箱注册，3:用户名注册',
`expired` tinyint(1) NOT NULL DEFAULT '0' COMMENT '密码是否已过期',
`phone` varchar(64) DEFAULT NULL COMMENT '用户输入的手机号',
`anonymous_uid` bigint(20) DEFAULT NULL COMMENT '对应匿名用户的UID',
`first_login` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否登录过 0:是, 1:否',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
`server` varchar(20) DEFAULT NULL COMMENT 'server信息',
`client` varchar(20) DEFAULT NULL COMMENT 'client信息',
`version` int(10) NOT NULL,
UNIQUE KEY `idx_user_name` (`user_name`),
KEY `idx_type` (`type`),
KEY `idx_user_id` (`user_id`),
KEY `idx_customer_id` (`fk_customer_id`),
KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证信息';
```
## 用户登录记录 user_login_record
## 用户登录设备信息 user_login_device_record
## 用户隐私协议 user_privacy_agreement
## 用户问题回答
# 商品域
## 产品基本信息 product_info
```
-- DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `abbrev_name` varchar(255) DEFAULT NULL COMMENT '产品简称',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `detail` varchar(1024) DEFAULT NULL COMMENT '详情',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '0.新建/1.待审核/2.审核通过/3.已上架/4.已下架',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除 0:未删除/1:已删除',
  `logo_url` varchar(1000) DEFAULT NULL,
  `fk_create_id` bigint(20) NOT NULL COMMENT '创建者',
  `fk_update_id` bigint(20) NOT NULL COMMENT '修改者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
  `server` varchar(20) DEFAULT NULL COMMENT 'server信息',
  `client` varchar(20) DEFAULT NULL COMMENT 'client信息',
  `version` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_state` (`state`),
  KEY `idx_create_id` (`fk_create_id`),
  KEY `idx_update_id` (`fk_update_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品基本信息';
```
## 商品 commodity
```
-- DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`fk_product_id` bigint(20) DEFAULT NULL COMMENT '产品标识',
`name` varchar(255) DEFAULT NULL COMMENT '商品名称',
`abbrev_name` varchar(255) DEFAULT NULL COMMENT '商品简称',
`fk_specs_type_id` bigint(20) DEFAULT NULL COMMENT '规格类型标识',
`fk_skuid` varchar(255) DEFAULT NULL COMMENT '规格标识',
`order_number` int(11) NOT NULL DEFAULT '1' COMMENT '排序值',
`price` decimal(10,2) DEFAULT NULL COMMENT '价格',
`currency` varchar(50) DEFAULT NULL COMMENT '货币',
`description` varchar(500) DEFAULT NULL COMMENT '商品说明',
`detail` varchar(5000) DEFAULT NULL COMMENT '商品详情',
`state` int(11) NOT NULL DEFAULT '0' COMMENT '状态, 0.新建/1.待审核/2.审核通过/3.已上架/4.已下架',
`deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除 0:未删除/1:已删除',
`fk_create_id` bigint(20) NOT NULL COMMENT '创建者',
 `fk_update_id` bigint(20) NOT NULL COMMENT '修改者',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
 `server` varchar(20) DEFAULT NULL COMMENT 'server信息',
 `client` varchar(20) DEFAULT NULL COMMENT 'client信息',PRIMARY KEY (`id`),
 `version` int(10) NOT NULL,
KEY `idx_product_id` (`fk_product_id`),
KEY `idx_specs_type_id` (`fk_specs_type_id`),
KEY `idx_skuid` (`fk_skuid`),
KEY `idx_create_id` (`fk_create_id`),
KEY `idx_update_id` (`fk_update_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息';
```
## 商品规格 commodity_specification
## 价格计划 price_plan
# 订单域
## 订单 order_info
```
-- DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fk_user_id` bigint(20) NOT NULL COMMENT '用户标识',
  `fk_user_account_id` bigint(20) NOT NULL COMMENT '用户账户标识',
  `currency` varchar(50) DEFAULT NULL COMMENT '货币',
  `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '扣减总金额',
  `shipping_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价值总金额',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付总金额',
  `activity_off` decimal(10,2) DEFAULT '0.00' COMMENT '活动扣减金额',
  `coupon_off` decimal(10,2) DEFAULT '0.00' COMMENT '优惠券扣减金额',
  `fk_pay_channel_id` varchar(50) DEFAULT NULL COMMENT '支付渠道',
  `fk_pay_seq` varchar(50) DEFAULT NULL COMMENT '交易流水号',
  `state` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态, 0.新建/1.下单成功/2.已支付/3.过期未支付',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除 0:未删除/1:已删除',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
 `server` varchar(20) DEFAULT NULL COMMENT 'server信息',
 `client` varchar(20) DEFAULT NULL COMMENT 'client信息',
 `version` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pay_seq` (`fk_pay_seq`) USING BTREE,
  KEY `idx_user_account_id` (`fk_user_account_id`) USING BTREE,
  KEY `idx_user_id` (`fk_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';
```
## 订单附加信息

## 订单项 order_iterm_info
```
-- DROP TABLE IF EXISTS `order_iterm_info`;
CREATE TABLE `order_iterm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_order_id` varchar(50) NOT NULL COMMENT '订单标识',
  `fk_commodity_id` bigint(20) NOT NULL COMMENT '商品标识',
  `discount_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '扣减总金额',
  `shipping_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价值总金额',
  `total_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付总金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 `trace_id` varchar(50) DEFAULT NULL COMMENT '调用链id',
 `server` varchar(20) DEFAULT NULL COMMENT 'server信息',
 `client` varchar(20) DEFAULT NULL COMMENT 'client信息',
 `version` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`fk_order_id`) USING BTREE,
  KEY `idx_commodity_id` (`fk_commodity_id`)  USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单项信息';

```
# 支付域
## 用户绑卡信息 user_card_info
## 订单支付记录 payment_record

