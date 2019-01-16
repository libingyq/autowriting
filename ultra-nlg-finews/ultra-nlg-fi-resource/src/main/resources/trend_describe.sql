/*
Navicat MySQL Data Transfer

Source Server         : 192.168.181.159#mysql#123456
Source Server Version : 50640
Source Host           : 192.168.181.159:3306
Source Database       : knowledge_base

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2018-11-02 09:39:34
*/

SET FOREIGN_KEY_CHECKS=0;

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

-- ----------------------------
-- Records of trend_describe
-- ----------------------------
INSERT INTO `trend_describe` VALUES ('1', '高走', '持续拉升');
INSERT INTO `trend_describe` VALUES ('2', '高走', '出现了一波直线拉升走高的形势');
INSERT INTO `trend_describe` VALUES ('3', '高走', '拉升走高');
INSERT INTO `trend_describe` VALUES ('4', '高走', '走强格局明显');
INSERT INTO `trend_describe` VALUES ('5', '高走', '预期向好，市场将逐渐走强，并冲击区间上轨');
INSERT INTO `trend_describe` VALUES ('6', '震荡式高走', '震荡攀升，人气回暖');
INSERT INTO `trend_describe` VALUES ('7', '震荡式高走', '持续走强将带动场内资金做多信心的进一步提升');
INSERT INTO `trend_describe` VALUES ('8', '震荡式高走', '震荡上扬，盘面回暖明显');
INSERT INTO `trend_describe` VALUES ('9', '震荡式高走', '维持震荡上扬态势');
INSERT INTO `trend_describe` VALUES ('10', '震荡式高走', '走强，有利于激发场内做多热情');
INSERT INTO `trend_describe` VALUES ('11', '震荡随后高走', '维持震荡随后高走');
INSERT INTO `trend_describe` VALUES ('12', '高走随后震荡', '震荡上行，随后高位整理');
INSERT INTO `trend_describe` VALUES ('13', '高走随后震荡', '一度冲高，随后维持高位震荡');
INSERT INTO `trend_describe` VALUES ('14', '冲高回落', '反弹冲高，随后再次震荡回落');
INSERT INTO `trend_describe` VALUES ('15', '短暂冲高随后回落', '拉高随后冲高回落');
INSERT INTO `trend_describe` VALUES ('16', '短暂冲高随后回落', '一度小幅拉升，之后又震荡回落');
INSERT INTO `trend_describe` VALUES ('17', '短暂冲高随后回落', '短暂冲高后震荡回落');
INSERT INTO `trend_describe` VALUES ('18', '短暂冲高随后回落', '拉高随后冲高回落');
INSERT INTO `trend_describe` VALUES ('19', '缓慢冲高随后回落', '缓慢冲高随后回落');
INSERT INTO `trend_describe` VALUES ('20', '冲高回落随后震荡', '一度冲高，随后回落维持震荡');
INSERT INTO `trend_describe` VALUES ('21', '震荡随后冲高回落', '走势震荡，一度上扬后下挫');
INSERT INTO `trend_describe` VALUES ('22', '触底反弹', '探底回升，由跌转涨');
INSERT INTO `trend_describe` VALUES ('23', '触底反弹', '探底反弹');
INSERT INTO `trend_describe` VALUES ('24', '触底反弹', '下挫随后V型反转');
INSERT INTO `trend_describe` VALUES ('25', '触底反弹', '走出一个标准的V形走势');
INSERT INTO `trend_describe` VALUES ('26', '触底反弹', '探底回升');
INSERT INTO `trend_describe` VALUES ('27', '触底反弹', '创阶段新低后强势抬升，走出“深V”行情');
INSERT INTO `trend_describe` VALUES ('28', '触底反弹', '震荡拉升，形成V型反转，市场人气也显著回升');
INSERT INTO `trend_describe` VALUES ('29', '触底反弹', '触底回升，反映消费升级动力依然存在');
INSERT INTO `trend_describe` VALUES ('30', '短暂触底随后反弹', '快速跳水之后反弹回升');
INSERT INTO `trend_describe` VALUES ('31', '短暂触底随后反弹', '缩量震荡探底，午后跌幅逐步收窄');
INSERT INTO `trend_describe` VALUES ('32', '短暂触底随后反弹', '短时探底后出现反弹');
INSERT INTO `trend_describe` VALUES ('33', '短暂触底随后反弹', '试探性回落，探底后便大幅拉升单边上扬');
INSERT INTO `trend_describe` VALUES ('34', '短暂触底随后反弹', '上演V型反转，大幅拉升，一举吹响反弹号角');
INSERT INTO `trend_describe` VALUES ('35', '缓慢触底随后反弹', '探底反弹，强势特征明显');
INSERT INTO `trend_describe` VALUES ('36', '缓慢触底随后反弹', '震荡下行，随后有所拉升');
INSERT INTO `trend_describe` VALUES ('37', '缓慢触底随后反弹', '震荡走低，盘中出现探底回升走势');
INSERT INTO `trend_describe` VALUES ('38', '触底反弹随后震荡', '触底回升，维持区间正当');
INSERT INTO `trend_describe` VALUES ('39', '触底反弹随后震荡', '盘中震荡走低，随后止跌回升');
INSERT INTO `trend_describe` VALUES ('40', '触底反弹随后震荡', '触底回升,市场有望恢复上涨');
INSERT INTO `trend_describe` VALUES ('41', '震荡随后触底反弹', '终于迎来探底反弹');
INSERT INTO `trend_describe` VALUES ('42', '震荡随后触底反弹', '正处于触底回升态势中，有利于强化各路资金的投资底气');
INSERT INTO `trend_describe` VALUES ('43', '低走', '跳水下挫');
INSERT INTO `trend_describe` VALUES ('44', '低走', '受跳水打击');
INSERT INTO `trend_describe` VALUES ('45', '低走', '下挫，持续走低');
INSERT INTO `trend_describe` VALUES ('46', '低走', '一度出现了快速下挫');
INSERT INTO `trend_describe` VALUES ('47', '低走', '出现大幅跳水');
INSERT INTO `trend_describe` VALUES ('48', '低走', '出现一波跳水行情');
INSERT INTO `trend_describe` VALUES ('49', '震荡式低走', '低走，延续近期低迷走势，跌幅不断扩大');
INSERT INTO `trend_describe` VALUES ('50', '震荡式低走', '震荡下行，跌幅持续扩大，盘面缺乏强势热点');
INSERT INTO `trend_describe` VALUES ('51', '震荡式低走', '震荡下行，市场避险情绪开始升温，人气再度低迷');
INSERT INTO `trend_describe` VALUES ('52', '震荡式低走', '震荡下行，跌幅持续扩大');
INSERT INTO `trend_describe` VALUES ('53', '震荡随后低走', '延续弱势震荡态势，之后震荡走低，加速下探');
INSERT INTO `trend_describe` VALUES ('54', '低走随后震荡', '低走，维持低位盘整格局');
INSERT INTO `trend_describe` VALUES ('55', '低走随后震荡', '维持低位震荡态势');
INSERT INTO `trend_describe` VALUES ('56', '低走随后震荡', '持续走低，维持低位震荡');
INSERT INTO `trend_describe` VALUES ('57', '低走随后震荡', '快速下跌，随后维持低位震荡');
INSERT INTO `trend_describe` VALUES ('58', '低走随后震荡', '出现一波跳水，保持在低位震荡');
INSERT INTO `trend_describe` VALUES ('59', '低走随后震荡', '呈现低位震荡格局');
INSERT INTO `trend_describe` VALUES ('60', '低走随后震荡', '展开低位震荡');
INSERT INTO `trend_describe` VALUES ('61', '震荡', '仍将维持震荡走势，等待更多的政策利好信号和流动性进一步改善');
INSERT INTO `trend_describe` VALUES ('62', '震荡', '宽幅震荡、持续磨底');
INSERT INTO `trend_describe` VALUES ('63', '震荡', '维持震荡走势');
INSERT INTO `trend_describe` VALUES ('64', '震荡', '在混沌的行情下,依旧维持震荡走势');
INSERT INTO `trend_describe` VALUES ('65', '涨跌互现', '涨跌互现，走势分化');
INSERT INTO `trend_describe` VALUES ('66', '涨跌互现', '整体涨跌互现');
