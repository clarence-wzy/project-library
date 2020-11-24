package com.wzy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Package: com.wzy.com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/11/26 20:25
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户实名
     */
    private String realName;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户信用度
     */
    private int credit;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户QQ
     */
    private String qq;
    /**
     * 用户WeChat
     */
    private String wechat;
    /**
     * 用户学生学号
     */
    private String studentId;
    /**
     * 用户学生密码
     */
    private String studentPassword;
    /**
     * 用户头像
     */
    private String headImage;
    /**
     * 用户对应的管理员id
     */
    private int adminId;
    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 角色id
     */
    private int roleId;
    /**
     * 角色名
     */
    private String roleName;

    /**
     * 用户发布的所有商品
     */
    private List<Product> productList;

}