/*
 Navicat Premium Data Transfer

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bots
-- ----------------------------
DROP TABLE IF EXISTS `bots`;
CREATE TABLE `bots`
(
    `id`             int(11)                                                   NOT NULL AUTO_INCREMENT,
    `login_no`       int(50)                                                   NULL DEFAULT NULL COMMENT 'qq号',
    `login_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '密码',
    `device_json`    varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `solt`           varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL DEFAULT NULL,
    `is_del`         varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT '0' COMMENT '0 正常 1 删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_longin_no` (`login_no`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pet_type
-- ----------------------------
DROP TABLE IF EXISTS `pet_type`;
CREATE TABLE `pet_type`
(
    `pet_type_id` int(11)                                                 NOT NULL COMMENT '种类id',
    `type_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '种类名称',
    PRIMARY KEY (`pet_type_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pets
-- ----------------------------
DROP TABLE IF EXISTS `pets`;
CREATE TABLE `pets`
(
    `pet_id`     int(11)                                                 NOT NULL AUTO_INCREMENT,
    `pet_name`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `pet_type`   int(11)                                                 NULL DEFAULT NULL COMMENT '宠物种类',
    `pet_rarity` int(2)                                                  NULL DEFAULT NULL COMMENT ' 0 普通 1稀有 2 史诗 3传说',
    PRIMARY KEY (`pet_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_pets
-- ----------------------------
DROP TABLE IF EXISTS `user_pets`;
CREATE TABLE `user_pets`
(
    `user_pet_id` int(11)                                                 NOT NULL AUTO_INCREMENT,
    `login_no`    int(50)                                                 NULL DEFAULT NULL COMMENT '用户id',
    `pet_name`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宠物名称',
    `pet_id`      int(11)                                                 NULL DEFAULT NULL COMMENT '宠物id',
    `pet_nike`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宠物昵称',
    PRIMARY KEY (`user_pet_id`) USING BTREE,
    INDEX `idx_login_no` (`login_no`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_points
-- ----------------------------
DROP TABLE IF EXISTS `user_points`;
CREATE TABLE `user_points`
(
    `id`       int(11)  NOT NULL AUTO_INCREMENT,
    `login_no` int(50)  NULL DEFAULT NULL COMMENT '用户名',
    `points`   int(255) NULL DEFAULT NULL COMMENT '积分',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
