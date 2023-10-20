/*
 Navicat Premium Data Transfer

 Source Server         : localhostMysql
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : shardingjdbc_0

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 20/10/2023 16:59:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_0
-- ----------------------------
DROP TABLE IF EXISTS `book_0`;
CREATE TABLE `book_0`  (
  `id` bigint NOT NULL,
  `book_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  `update_date` datetime NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_0
-- ----------------------------
INSERT INTO `book_0` VALUES (2, '三国演绎', '不知道', '三国演绎', 60.00, 90, '2023-04-03 20:16:39', '2023-04-03 20:16:39');
INSERT INTO `book_0` VALUES (1642862539685748736, '三国演义', '不知道', '三国演义', 30.00, 50, '2023-04-03 20:12:07', '2023-04-03 20:12:07');
INSERT INTO `book_0` VALUES (1642863189135974400, '红楼梦', '曹雪芹', '红楼梦', 60.00, 88, '2023-04-03 20:14:42', '2023-04-03 20:14:42');

-- ----------------------------
-- Table structure for book_1
-- ----------------------------
DROP TABLE IF EXISTS `book_1`;
CREATE TABLE `book_1`  (
  `id` bigint NOT NULL,
  `book_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  `update_date` datetime NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_1
-- ----------------------------
INSERT INTO `book_1` VALUES (1642862822398615552, '西游记', '不知道', '西游记', 60.00, 31, '2023-04-03 20:13:14', '2023-04-03 20:13:14');

SET FOREIGN_KEY_CHECKS = 1;
