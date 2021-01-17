package com.ripen.dao.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统角色权限实体类
 *
 * @author Ripen.Y
 * @version 2021/01/17 14:34
 * @since 2021/01/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysRolePerm implements Serializable {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 角色ID
     */
    private int roleId;

    /**
     * 权限ID
     */
    private int permId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色详情
     */
    private String roleDetail;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限详情
     */
    private String permDetail;

    /**
     * 用户类型，0：用户，1：管理员
     */
    private int type;

}
