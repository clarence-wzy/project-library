/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : 65001

 Date: 17/01/2021 16:50:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bk_comment
-- ----------------------------
DROP TABLE IF EXISTS `bk_comment`;
CREATE TABLE `bk_comment`  (
  `id` int(11) NOT NULL COMMENT '自增id',
  `rcd_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '记录id',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `rate` int(1) NOT NULL COMMENT '评论等级，-1：差评，0：中评，1：好评',
  `content` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `img` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `is_anno` int(1) NOT NULL DEFAULT 0 COMMENT '是否匿名，0：否，1：是',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：删除'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bk_detail
-- ----------------------------
DROP TABLE IF EXISTS `bk_detail`;
CREATE TABLE `bk_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ser_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍编号',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：借出',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `adm_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bk_info
-- ----------------------------
DROP TABLE IF EXISTS `bk_info`;
CREATE TABLE `bk_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `bk_id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `bk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍名称',
  `bk_author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍作者',
  `bk_press` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍出版社',
  `bk_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '书籍报价',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bk_record
-- ----------------------------
DROP TABLE IF EXISTS `bk_record`;
CREATE TABLE `bk_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ser_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍编号',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `lend_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '借出时间',
  `return_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '归还时间',
  `expire_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '过期时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bk_type
-- ----------------------------
DROP TABLE IF EXISTS `bk_type`;
CREATE TABLE `bk_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '书籍类型id',
  `t_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍类型名称',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bk_type_info
-- ----------------------------
DROP TABLE IF EXISTS `bk_type_info`;
CREATE TABLE `bk_type_info`  (
  `bt_id` int(11) NOT NULL COMMENT '书籍类型id',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍类型与信息关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `adm_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员id',
  `pwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sex` int(1) NULL DEFAULT NULL COMMENT '性别，0：男，1：女',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：锁定，2：删除',
  `level` int(1) NOT NULL DEFAULT 0 COMMENT '等级（暂无等级，默认都为0）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNI_ADMIN_ID`(`adm_id`) USING BTREE COMMENT '管理员id唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm`;
CREATE TABLE `sys_perm`  (
  `perm_id` int(11) NOT NULL AUTO_INCREMENT,
  `perm_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `perm_detail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`perm_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_perm
-- ----------------------------
INSERT INTO `sys_perm` VALUES (1, 'SE_BK', '查询书籍');
INSERT INTO `sys_perm` VALUES (2, 'AD_BK', '新增书籍');
INSERT INTO `sys_perm` VALUES (3, 'UP_BK', '修改书籍');
INSERT INTO `sys_perm` VALUES (4, 'LE_RE_BK', '借阅归还书籍');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_detail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'GE_USER', '普通用户');

-- ----------------------------
-- Table structure for sys_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm`;
CREATE TABLE `sys_role_perm`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `perm_id` int(11) NULL DEFAULT NULL COMMENT '权限ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
INSERT INTO `sys_role_perm` VALUES (1, 1);
INSERT INTO `sys_role_perm` VALUES (1, 4);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `pwd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `sex` int(1) NOT NULL DEFAULT 0 COMMENT '性别，0：男，1：女',
  `head_img` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：锁定，2：删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNI_ACCOUNT`(`account`) USING BTREE COMMENT '用户账号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `type` int(1) NOT NULL COMMENT '用户类型，0：用户，1：管理员'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('00001', 1, 0);

-- ----------------------------
-- Table structure for u_operation
-- ----------------------------
DROP TABLE IF EXISTS `u_operation`;
CREATE TABLE `u_operation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `op_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作id',
  `op_type` int(11) NOT NULL COMMENT '操作类型',
  `op_detail` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作详情',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作日志表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
