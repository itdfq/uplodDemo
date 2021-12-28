/*
 Navicat Premium Data Transfer

 Source Server         : duan-mysql
 Source Server Type    : MySQL
 Source Server Version : 50709
 Source Host           : 121.36.77.21:5019
 Source Schema         : dfq

 Target Server Type    : MySQL
 Target Server Version : 50709
 File Encoding         : 65001

 Date: 28/12/2021 21:30:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for upload
-- ----------------------------
DROP TABLE IF EXISTS `upload`;
CREATE TABLE `upload`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名字',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'UUID',
  `size` int(11) NOT NULL COMMENT '文件大小',
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件类型',
  `save_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件保存地址',
  `add_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_deleted`(`id`, `deleted_id`) USING BTREE,
  UNIQUE INDEX `code_deleted`(`code`, `deleted_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
