/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50628

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bots
-- ----------------------------
DROP TABLE IF EXISTS `bots`;
CREATE TABLE `bots`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_no` int(50) NULL DEFAULT NULL COMMENT 'qq号',
  `login_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `device_json` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `solt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_del` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0 正常 1 删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_longin_no`(`login_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '机器人表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for jokes
-- ----------------------------
DROP TABLE IF EXISTS `jokes`;
CREATE TABLE `jokes`  (
  `id` bigint(36) UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '0 每日提示 1 故事 2 对话',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0 未使用 1 已使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pet_type
-- ----------------------------
DROP TABLE IF EXISTS `pet_type`;
CREATE TABLE `pet_type`  (
  `pet_type_id` int(11) NOT NULL COMMENT '种类id',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '种类名称',
  PRIMARY KEY (`pet_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '宠物类型' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for pets
-- ----------------------------
DROP TABLE IF EXISTS `pets`;
CREATE TABLE `pets`  (
  `pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pet_type` int(11) NULL DEFAULT NULL COMMENT '宠物种类',
  `pet_rarity` int(2) NULL DEFAULT NULL COMMENT ' 0 普通 1稀有 2 史诗 3传说',
  `hp` int(15) NULL DEFAULT NULL COMMENT '血量',
  `p_atk` int(15) NULL DEFAULT NULL COMMENT '物攻',
  `m_atk` int(15) NULL DEFAULT NULL COMMENT '法攻',
  `p_def` int(15) NULL DEFAULT NULL COMMENT '物防',
  `m_def` int(15) NULL DEFAULT NULL COMMENT '魔防',
  `speed` int(15) NULL DEFAULT NULL COMMENT '速度',
  PRIMARY KEY (`pet_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 515 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '宠物表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for rights
-- ----------------------------
DROP TABLE IF EXISTS `rights`;
CREATE TABLE `rights`  (
  `right_id` int(11) NOT NULL COMMENT '权限id',
  `right_cmd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`right_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_rights
-- ----------------------------
DROP TABLE IF EXISTS `role_rights`;
CREATE TABLE `role_rights`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `role_id` int(15) NULL DEFAULT NULL COMMENT '角色id',
  `right_id` int(15) NULL DEFAULT NULL COMMENT '角色权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `role_id` int(10) NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(13) UNSIGNED NOT NULL AUTO_INCREMENT,
  `config_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `config_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `login_no` int(50) NULL DEFAULT NULL COMMENT 'qq',
  `role` int(2) NULL DEFAULT NULL COMMENT '角色 ',
  `nick` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_login_no`(`login_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_ability
-- ----------------------------
DROP TABLE IF EXISTS `user_ability`;
CREATE TABLE `user_ability`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login_no` int(50) NULL DEFAULT NULL COMMENT '用户名',
  `auto_translation` int(2) NULL DEFAULT NULL COMMENT '是否开启自动翻译  0 关闭 1 开启',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_pets
-- ----------------------------
DROP TABLE IF EXISTS `user_pets`;
CREATE TABLE `user_pets`  (
  `user_pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_no` int(50) NULL DEFAULT NULL COMMENT '用户id',
  `pet_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宠物名称',
  `pet_id` int(11) NULL DEFAULT NULL COMMENT '宠物id',
  `pet_nike` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宠物昵称',
  `is_main` int(2) NULL DEFAULT 0 COMMENT '是否是主宠 0 否 1 是',
  `is_delete` int(2) NULL DEFAULT 0 COMMENT ' 0 正常 1已删除',
  PRIMARY KEY (`user_pet_id`) USING BTREE,
  INDEX `idx_login_no`(`login_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 526 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户宠物表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_points
-- ----------------------------
DROP TABLE IF EXISTS `user_points`;
CREATE TABLE `user_points`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_no` int(50) NULL DEFAULT NULL COMMENT '用户名',
  `points` int(255) NULL DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户积分表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_text_content_log
-- ----------------------------
DROP TABLE IF EXISTS `user_text_content_log`;
CREATE TABLE `user_text_content_log`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '号码',
  `nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `text_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文字记录',
  `creat_day` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建日期yyyyMMdd',
  `creat_time` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建时间 HHmmss',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_text_content_pic
-- ----------------------------
DROP TABLE IF EXISTS `user_text_content_pic`;
CREATE TABLE `user_text_content_pic`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_text_content_log_id` bigint(32) UNSIGNED NULL DEFAULT NULL,
  `url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bucket_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储桶',
  `bucket_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储桶中唯一标识',
  `pic_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息关联的图片' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
