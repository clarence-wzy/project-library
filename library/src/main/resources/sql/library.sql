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

 Date: 31/01/2021 17:16:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bk_comment
-- ----------------------------
DROP TABLE IF EXISTS `bk_comment`;
CREATE TABLE `bk_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `rcd_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '记录id',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `rate` int(1) NOT NULL COMMENT '评论等级，-1：差评，0：中评，1：好评',
  `content` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `img` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `is_anno` int(1) NOT NULL DEFAULT 0 COMMENT '是否匿名，0：否，1：是',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_comment
-- ----------------------------
INSERT INTO `bk_comment` VALUES (1, 'c_553025012328837120', 'b_552905377247932416', 1, '评价A', NULL, '00001', 0, '2021-01-30 22:45:03', 0);

-- ----------------------------
-- Table structure for bk_detail
-- ----------------------------
DROP TABLE IF EXISTS `bk_detail`;
CREATE TABLE `bk_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ser_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍编号',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '书籍id',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：借出',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `adm_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_detail
-- ----------------------------
INSERT INTO `bk_detail` VALUES (7, 's552905378355228672', 'b_552905377247932416', 1, '2021-01-30 14:49:40', '1');
INSERT INTO `bk_detail` VALUES (8, 's552905378355228673', 'b_552905377247932416', 0, '2021-01-30 14:49:40', '1');
INSERT INTO `bk_detail` VALUES (9, 's553069033482891264', 'b_553069033336090624', 0, '2021-01-31 01:39:59', '1');
INSERT INTO `bk_detail` VALUES (10, 's553069033482891265', 'b_553069033336090624', 0, '2021-01-31 01:39:59', '1');
INSERT INTO `bk_detail` VALUES (11, 's553069033487085568', 'b_553069033336090624', 0, '2021-01-31 01:39:59', '1');
INSERT INTO `bk_detail` VALUES (12, 's553069448484106240', 'b_553069448463134720', 0, '2021-01-31 01:41:38', '1');
INSERT INTO `bk_detail` VALUES (13, 's553069448484106241', 'b_553069448463134720', 0, '2021-01-31 01:41:38', '1');
INSERT INTO `bk_detail` VALUES (14, 's553069448484106242', 'b_553069448463134720', 0, '2021-01-31 01:41:38', '1');
INSERT INTO `bk_detail` VALUES (15, 's553069448484106243', 'b_553069448463134720', 0, '2021-01-31 01:41:38', '1');

-- ----------------------------
-- Table structure for bk_info
-- ----------------------------
DROP TABLE IF EXISTS `bk_info`;
CREATE TABLE `bk_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `bk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍名称',
  `bk_author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍作者',
  `bk_press` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍出版社',
  `bk_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '书籍报价',
  `bk_img` varchar(1020) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '书籍图片',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_info
-- ----------------------------
INSERT INTO `bk_info` VALUES (8, 'b_552905377247932416', '书籍A-1', '作者A', '出版社A', 20.15, NULL, '备注A', 0, '2021-01-30 20:03:34', '2021-01-30 20:03:25');
INSERT INTO `bk_info` VALUES (9, 'b_553069033336090624', '书籍B', '作者B', '出版社B', 50.25, NULL, NULL, 0, '2021-01-31 01:39:59', '2021-01-31 01:39:59');
INSERT INTO `bk_info` VALUES (10, 'b_553069448463134720', '书籍C', '作者C', '出版社C', 60.59, NULL, NULL, 0, '2021-01-31 01:41:38', '2021-01-31 01:41:38');

-- ----------------------------
-- Table structure for bk_record
-- ----------------------------
DROP TABLE IF EXISTS `bk_record`;
CREATE TABLE `bk_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `rcd_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍记录id',
  `account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `ser_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍编号',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id',
  `lend_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '借出时间',
  `return_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '归还时间',
  `expire_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '过期时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_record
-- ----------------------------
INSERT INTO `bk_record` VALUES (9, 'r_553060462842097664', '00001', 's552905378355228672', 'b_552905377247932416', '2021-01-31 01:05:55', NULL, '2021-02-05 12:00:00', '2021-01-31 01:05:55');
INSERT INTO `bk_record` VALUES (10, 'r_553060462842097665', '00001', 's552905378355228673', 'b_552905377247932416', '2021-01-31 01:05:55', '2021-01-31 02:07:27', '2021-02-05 12:00:00', '2021-01-31 02:07:32');
INSERT INTO `bk_record` VALUES (11, 'r_553069915696017408', '00001', 's553069448484106241', 'b_553069448463134720', '2021-01-31 01:43:29', '2021-01-31 02:07:27', '2021-02-10 08:33:48', '2021-01-31 02:07:33');

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
) ENGINE = InnoDB AUTO_INCREMENT = 10004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_type
-- ----------------------------
INSERT INTO `bk_type` VALUES (10001, '类型A-1', 0, '2021-01-30 14:14:23', '2021-01-30 19:33:10');
INSERT INTO `bk_type` VALUES (10002, '类型B', 0, '2021-01-30 18:27:13', '2021-01-30 18:27:13');
INSERT INTO `bk_type` VALUES (10003, '类型C', 0, '2021-01-30 18:28:11', '2021-01-30 20:04:44');

-- ----------------------------
-- Table structure for bk_type_info
-- ----------------------------
DROP TABLE IF EXISTS `bk_type_info`;
CREATE TABLE `bk_type_info`  (
  `bt_id` int(11) NOT NULL COMMENT '书籍类型id',
  `bk_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '书籍id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '书籍类型与信息关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bk_type_info
-- ----------------------------
INSERT INTO `bk_type_info` VALUES (10001, 'b_552905377247932416');
INSERT INTO `bk_type_info` VALUES (10002, 'b_553069033336090624');
INSERT INTO `bk_type_info` VALUES (10003, 'b_553069033336090624');
INSERT INTO `bk_type_info` VALUES (10003, 'b_553069448463134720');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, '00001', '9Poqh0+JsC4=', 0, '2@qq.com', 0, 0, '2021-01-16 15:19:17', '2021-01-16 15:30:26');
INSERT INTO `sys_admin` VALUES (2, '00002', '9Poqh0+JsC4=', 0, '1@qq.com', 0, 0, '2021-01-30 23:19:38', '2021-01-30 23:19:38');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

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
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0：正常，1：锁定，2：删除',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNI_ACCOUNT`(`account`) USING BTREE COMMENT '用户账号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '00001', '9Poqh0+JsC4=', 'ripen1', 0, NULL, 0, '2021-01-16 15:33:47', '2021-01-16 15:34:04');
INSERT INTO `sys_user` VALUES (2, '00002', '9Poqh0+JsC4=', 'ripen2', 0, NULL, 0, '2021-01-30 23:03:54', '2021-01-30 23:03:54');

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
  `account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作日志表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
