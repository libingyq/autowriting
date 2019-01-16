/*
Navicat MySQL Data Transfer

Source Server         : 192.168.181.159#mysql#123456
Source Server Version : 50640
Source Host           : 192.168.181.159:3306
Source Database       : knowledge_base

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-11-02 09:38:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grab_log
-- ----------------------------
DROP TABLE IF EXISTS `grab_log`;
CREATE TABLE `grab_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grab_time` datetime DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `retry` int(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2045 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_index
-- ----------------------------
DROP TABLE IF EXISTS `share_index`;
CREATE TABLE `share_index` (
  `code` varchar(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '股票名称',
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '时间日期',
  `change_percent` double DEFAULT NULL COMMENT '涨跌幅',
  `open` double DEFAULT NULL COMMENT '开盘价',
  `high` double DEFAULT NULL COMMENT '最高价',
  `low` double DEFAULT NULL COMMENT '最低价',
  `close` double DEFAULT NULL COMMENT '收盘价',
  `volume` double DEFAULT NULL COMMENT '成交量',
  `amount` double DEFAULT NULL COMMENT '成交量（亿元）',
  `settlement` double DEFAULT NULL COMMENT '昨日收盘价',
  PRIMARY KEY (`time`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_target
-- ----------------------------
DROP TABLE IF EXISTS `share_target`;
CREATE TABLE `share_target` (
  `code` varchar(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '股票名称',
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '时间日期',
  `change_percent` double DEFAULT NULL COMMENT '涨跌幅',
  `open` double DEFAULT NULL COMMENT '开盘价',
  `high` double DEFAULT NULL COMMENT '最高价',
  `low` double DEFAULT NULL COMMENT '最低价',
  `close` double DEFAULT NULL COMMENT '收盘价',
  `volume` int(11) DEFAULT NULL COMMENT '成交量',
  `turnover` double DEFAULT NULL COMMENT '换手率',
  `price_change` double DEFAULT NULL COMMENT '价格变动',
  PRIMARY KEY (`time`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_ticker
-- ----------------------------
DROP TABLE IF EXISTS `share_ticker`;
CREATE TABLE `share_ticker` (
  `code` varchar(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '股票名称',
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '时间日期',
  `change_percent` double DEFAULT NULL COMMENT '涨跌幅',
  `open` double DEFAULT NULL COMMENT '开盘价',
  `high` double DEFAULT NULL COMMENT '最高价',
  `low` double DEFAULT NULL COMMENT '最低价',
  `close` double DEFAULT NULL COMMENT '收盘价',
  `volume` double DEFAULT NULL COMMENT '成交量',
  `turnover` double DEFAULT NULL COMMENT '换手率',
  `amount` double DEFAULT NULL COMMENT '成交量（亿元）',
  `settlement` double DEFAULT NULL COMMENT '昨日收盘价',
  PRIMARY KEY (`time`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_ticker_tencent
-- ----------------------------
DROP TABLE IF EXISTS `share_ticker_tencent`;
CREATE TABLE `share_ticker_tencent` (
  `code` varchar(10) NOT NULL COMMENT '股票代码',
  `name` varchar(20) DEFAULT NULL COMMENT '股票名称',
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '时间日期',
  `change_percent` double DEFAULT NULL COMMENT '涨跌幅（%）',
  `open` double DEFAULT NULL COMMENT '开盘价',
  `high` double DEFAULT NULL COMMENT '最高价',
  `low` double DEFAULT NULL COMMENT '最低价',
  `close` double DEFAULT NULL COMMENT '现价',
  `volume` double DEFAULT NULL COMMENT '成交量（手）',
  `turnover` double DEFAULT NULL COMMENT '换手率（%）',
  `amount` double DEFAULT NULL COMMENT '成交额（万元）',
  `settlement` double DEFAULT NULL COMMENT '昨日收盘价',
  PRIMARY KEY (`time`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_trend
-- ----------------------------
DROP TABLE IF EXISTS `share_trend`;
CREATE TABLE `share_trend` (
  `description` text,
  `figures_tdx_index` bigint(20) DEFAULT NULL,
  `data` text,
  `name` text,
  `time` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_trend_old
-- ----------------------------
DROP TABLE IF EXISTS `share_trend_old`;
CREATE TABLE `share_trend_old` (
  `description` text,
  `figures_tdx_index` bigint(20) DEFAULT NULL,
  `data` text,
  `name` text,
  `time` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for share_trend_threshold
-- ----------------------------
DROP TABLE IF EXISTS `share_trend_threshold`;
CREATE TABLE `share_trend_threshold` (
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `time_str` text,
  `description` text,
  `name` text,
  `data` text,
  `threshold` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for shares_knowledge_base
-- ----------------------------
DROP TABLE IF EXISTS `shares_knowledge_base`;
CREATE TABLE `shares_knowledge_base` (
  `id` varchar(20) NOT NULL COMMENT '主键',
  `code` varchar(10) NOT NULL COMMENT '股票代码',
  `name` varchar(20) NOT NULL COMMENT '股票名称',
  `area` varchar(10) DEFAULT NULL COMMENT '地域',
  `share_type` varchar(10) DEFAULT NULL COMMENT '股票类型',
  `industry` varchar(20) DEFAULT NULL COMMENT '行业',
  `concepts` varchar(500) DEFAULT NULL COMMENT '概念',
  `eal_component` varchar(10) DEFAULT NULL COMMENT '指数成分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tdx_index
-- ----------------------------
DROP TABLE IF EXISTS `tdx_index`;
CREATE TABLE `tdx_index` (
  `time` datetime DEFAULT NULL,
  `close` double DEFAULT NULL,
  `name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tdx_index_group_by_day
-- ----------------------------
DROP TABLE IF EXISTS `tdx_index_group_by_day`;
CREATE TABLE `tdx_index_group_by_day` (
  `data` text,
  `name` text,
  `time` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `paragraph_ids` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for template_paragraph
-- ----------------------------
DROP TABLE IF EXISTS `template_paragraph`;
CREATE TABLE `template_paragraph` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `sentence_ids` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for template_sentence
-- ----------------------------
DROP TABLE IF EXISTS `template_sentence`;
CREATE TABLE `template_sentence` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `exp` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trend_describe
-- ----------------------------
DROP TABLE IF EXISTS `trend_describe`;
CREATE TABLE `trend_describe` (
  `id` bigint(20) NOT NULL,
  `trend` text NOT NULL,
  `describe` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
