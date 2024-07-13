/*
 Navicat Premium Data Transfer

 Source Server         : 1234
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 13/06/2024 19:18:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bookname` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `bookpress` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `pressdate` date NULL DEFAULT NULL,
  `bookauthor` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `bookcount` int NULL DEFAULT NULL,
  `bookimage` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (1, 'qwewq', 'adda', '2024-02-02', 'qeqwas', 2, 'http://localhost:8082/untitled_war\\images\\1.jpg');
INSERT INTO `books` VALUES (2, '数据库', '清华出版社', '1998-10-30', '周杰伦', 2, './images/数据库.jpg');
INSERT INTO `books` VALUES (3, '徐志摩诗集', '永强出版社', '1995-03-04', '徐志摩', 1, './images/徐志摩诗集.jpg');
INSERT INTO `books` VALUES (4, '高等数学', '晶晶出版社', '2011-07-04', '周传雄', 3, './images/高等数学.jpg');
INSERT INTO `books` VALUES (5, '中国历史', '人民出版社', '2016-06-22', '温家宝', 2, './images/中国历史.jpg');
INSERT INTO `books` VALUES (6, '盗梦空间', '神话出版社', '2014-08-28', 'mary', 1, './images/盗梦空间.jpg');
INSERT INTO `books` VALUES (7, '西游记', '人民出版社', '2001-12-01', '吴承恩', 17, './images/西游记.jpg');
INSERT INTO `books` VALUES (8, '水浒传', '贵州出版社', '1977-04-04', '施耐庵', 5, './images/水浒传.jpg');
INSERT INTO `books` VALUES (9, '红楼梦', '上海出版社', '1966-04-11', '曹雪芹', 8, './images/红楼梦.jpg');
INSERT INTO `books` VALUES (10, '三国演义', '宇宙无敌出版社', '2001-09-09', '罗贯中', 13, './images/三国演义.jpg');
INSERT INTO `books` VALUES (11, '23', '32', '2024-02-02', '32', 123, './images/三国演义.jpg');
INSERT INTO `books` VALUES (15, '企鹅舞', 'eqw', '2002-02-02', 'weq', 2, 'http://localhost:8080/untitled_war\\images\\1.jpg');
INSERT INTO `books` VALUES (16, 'eqw', 'qew', '2002-02-02', 'eqw', 3, 'http://localhost:8080/untitled_war\\images\\cy.png');
INSERT INTO `books` VALUES (17, '为桥', 'ewq', '2002-02-02', 'q', 2, 'http://localhost:8080/untitled_war\\images\\cy.png');
INSERT INTO `books` VALUES (18, 'sdfsd', 'sdfds', '2022-02-02', 'sdf', 1, 'http://localhost:8080/untitled_war\\uploads\\QQ图片20240412172011.jpg');
INSERT INTO `books` VALUES (19, 'tre', 'etr', '2022-02-02', 'er', 1, 'http://localhost:8080/untitled_war\\uploads\\QQ图片20240111225615.png');
INSERT INTO `books` VALUES (20, 'rte', 'ert', '2020-02-02', 'wer', 2, 'http://localhost:8080/untitled_war\\images\\QQ图片20240414084252.jpg');
INSERT INTO `books` VALUES (21, 'gdf', 'dgf', '2022-02-02', 'wqe', 1, 'http://localhost:8080/untitled_war\\images\\QQ图片20240206221101.png');

-- ----------------------------
-- Table structure for resadmin
-- ----------------------------
DROP TABLE IF EXISTS `resadmin`;
CREATE TABLE `resadmin`  (
  `raid` int NOT NULL AUTO_INCREMENT,
  `raname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rapwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`raid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resadmin
-- ----------------------------

-- ----------------------------
-- Table structure for resfood
-- ----------------------------
DROP TABLE IF EXISTS `resfood`;
CREATE TABLE `resfood`  (
  `fid` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `normprice` decimal(8, 2) NULL DEFAULT NULL,
  `realprice` decimal(8, 2) NULL DEFAULT NULL,
  `detail` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fphoto` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resfood
-- ----------------------------
INSERT INTO `resfood` VALUES (1, '素炒莴笋丝', 22.00, 20.00, '营养丰富', '500008.jpg');
INSERT INTO `resfood` VALUES (2, '蛋炒饭', 22.00, 20.00, '营养丰富', '500022.jpg');
INSERT INTO `resfood` VALUES (3, '酸辣鱼', 42.00, 40.00, '营养丰富', '500023.jpg');
INSERT INTO `resfood` VALUES (4, '鲁粉', 12.00, 10.00, '营养丰富', '500024.jpg');
INSERT INTO `resfood` VALUES (5, '西红柿蛋汤', 12.00, 10.00, '营养丰富', '500025.jpg');
INSERT INTO `resfood` VALUES (6, '炖鸡', 102.00, 100.00, '营养丰富', '500026.jpg');
INSERT INTO `resfood` VALUES (7, '炒鸡', 12.00, 10.00, '营养丰富', '500033.jpg');
INSERT INTO `resfood` VALUES (8, '炒饭', 12.00, 10.00, '营养丰富', '500034.jpg');
INSERT INTO `resfood` VALUES (9, '手撕前女友', 12.00, 10.00, '营养丰富', '500035.jpg');
INSERT INTO `resfood` VALUES (10, '面条', 12.00, 10.00, '营养丰富', '500036.jpg');
INSERT INTO `resfood` VALUES (11, '端菜', 12.00, 10.00, '营养丰富', '500038.jpg');
INSERT INTO `resfood` VALUES (12, '酸豆角', 12.00, 10.00, '营养丰富', '500041.jpg');

-- ----------------------------
-- Table structure for resorder
-- ----------------------------
DROP TABLE IF EXISTS `resorder`;
CREATE TABLE `resorder`  (
  `roid` int NOT NULL AUTO_INCREMENT,
  `userid` int NULL DEFAULT NULL,
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tel` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ordertime` date NULL DEFAULT NULL,
  `deliverytime` date NULL DEFAULT NULL,
  `ps` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`roid`) USING BTREE,
  INDEX `fk_resorder`(`userid`) USING BTREE,
  CONSTRAINT `fk_resorder` FOREIGN KEY (`userid`) REFERENCES `resuser` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resorder
-- ----------------------------
INSERT INTO `resorder` VALUES (1, 1, '湖南省衡阳市', '13878789999', '2024-06-10', '2024-06-10', '送餐上门', 0);
INSERT INTO `resorder` VALUES (2, NULL, '湖南', '0731-84802110', '2024-06-11', '2024-06-01', '速度', 0);
INSERT INTO `resorder` VALUES (3, NULL, '湖南', '0731-84802110', '2024-06-11', '2024-06-01', '速度', 0);
INSERT INTO `resorder` VALUES (4, 1, '湖南', '0731-84802110', '2024-06-11', '2024-06-01', '速度', 0);
INSERT INTO `resorder` VALUES (5, 1, '湖南', '0731-84802110', '2024-06-11', '2024-06-01', '速度', 0);
INSERT INTO `resorder` VALUES (6, 1, '湖南', '0731-84802110', '2024-06-11', '2024-06-01', '速度', 0);
INSERT INTO `resorder` VALUES (7, 1, '湖南', '0731-84802110', '2024-06-11', '2024-06-01', '速度', 0);

-- ----------------------------
-- Table structure for resorderitem
-- ----------------------------
DROP TABLE IF EXISTS `resorderitem`;
CREATE TABLE `resorderitem`  (
  `roiid` int NOT NULL AUTO_INCREMENT,
  `roid` int NULL DEFAULT NULL,
  `fid` int NULL DEFAULT NULL,
  `dealprice` decimal(8, 2) NULL DEFAULT NULL,
  `num` int NULL DEFAULT NULL,
  PRIMARY KEY (`roiid`) USING BTREE,
  INDEX `fk_resorderitem_roid`(`roid`) USING BTREE,
  INDEX `fk_tbl_res_fid`(`fid`) USING BTREE,
  CONSTRAINT `fk_resorderitem_roid` FOREIGN KEY (`roid`) REFERENCES `resorder` (`roid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_tbl_res_fid` FOREIGN KEY (`fid`) REFERENCES `resfood` (`fid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resorderitem
-- ----------------------------
INSERT INTO `resorderitem` VALUES (1, 1, 1, 20.00, 1);
INSERT INTO `resorderitem` VALUES (2, 1, 2, 20.00, 1);
INSERT INTO `resorderitem` VALUES (3, 2, 1, 20.00, 1);
INSERT INTO `resorderitem` VALUES (4, 3, 1, 20.00, 1);
INSERT INTO `resorderitem` VALUES (5, 4, 1, 20.00, 2);
INSERT INTO `resorderitem` VALUES (6, 5, 1, 20.00, 2);
INSERT INTO `resorderitem` VALUES (7, 6, 1, 20.00, 2);
INSERT INTO `resorderitem` VALUES (8, 7, 1, 20.00, 2);

-- ----------------------------
-- Table structure for resuser
-- ----------------------------
DROP TABLE IF EXISTS `resuser`;
CREATE TABLE `resuser`  (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resuser
-- ----------------------------
INSERT INTO `resuser` VALUES (1, 'a', 'c2f0789e6ad28c3f6f85da1fb9828d79', '@qq.com');
INSERT INTO `resuser` VALUES (2, 'b', 'c2f0789e6ad28c3f6f85da1fb9828d79', '@qq.com');

-- ----------------------------
-- Table structure for testuser
-- ----------------------------
DROP TABLE IF EXISTS `testuser`;
CREATE TABLE `testuser`  (
  `userid` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `upwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testuser
-- ----------------------------
INSERT INTO `testuser` VALUES (1, 'a', 'c2f0789e6ad28c3f6f85da1fb9828d79');
INSERT INTO `testuser` VALUES (2, 'b', 'dc0ae7e1387be9b795f5d6299e383759');
INSERT INTO `testuser` VALUES (3, 'c', 'ed108f6919ebadc8e809f8b86ef40b05');
INSERT INTO `testuser` VALUES (4, 'd', '39abe4bca904bca5a11121955a2996bf');
INSERT INTO `testuser` VALUES (5, 'e', 'a94837b18f8f43f29448b40a6e7386ba');
INSERT INTO `testuser` VALUES (6, 'f', '83be264eb452fcf0a1c322f2c7cbf987');
INSERT INTO `testuser` VALUES (7, 'r', '71b0438bf46aa26928c7f5a371d619e1');
INSERT INTO `testuser` VALUES (8, 'q', '66eee9ace7508c154d02022000e1cf86');
INSERT INTO `testuser` VALUES (9, 'w', '66eee9ace7508c154d02022000e1cf86');
INSERT INTO `testuser` VALUES (10, 'dd', '28c8edde3d61a0411511d3b1866f0636');
INSERT INTO `testuser` VALUES (11, '1', '28c8edde3d61a0411511d3b1866f0636');
INSERT INTO `testuser` VALUES (12, 'dd123', '28c8edde3d61a0411511d3b1866f0636');

SET FOREIGN_KEY_CHECKS = 1;
