/*
Navicat MySQL Data Transfer

Source Server         : 192.168.181.159#mysql#123456
Source Server Version : 50640
Source Host           : 192.168.181.159:3306
Source Database       : knowledge_base

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-11-02 09:40:01
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of template_sentence
-- ----------------------------
INSERT INTO `template_sentence` VALUES ('1', '1', '<{早盘}+[{index:399106},{stock:603978}]+[{高开高走},{涨幅},{跌幅}]>', '2018-10-18 17:46:06', '2018-10-18 17:46:08');
INSERT INTO `template_sentence` VALUES ('2', '1', '<{截止发稿}+[{index:000002}]+[{报点}]>', '2018-10-18 17:46:31', '2018-10-18 17:46:33');
INSERT INTO `template_sentence` VALUES ('3', '1', '<{截止发稿}+[{index:000001}]+[{报点}]>', '2018-10-18 17:53:48', '2018-10-18 17:53:52');
INSERT INTO `template_sentence` VALUES ('4', '1', '<{早盘}+[{index:399106}]+[{高开高走},{涨幅},{跌幅}]>', '2018-10-18 17:53:54', '2018-10-18 17:53:57');
