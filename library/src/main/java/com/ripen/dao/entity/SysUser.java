package com.ripen.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:22
 * @since 2021/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysUser implements Serializable {

    /**
     * 账号
     */
    private String account;

    private String pwd;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别，0：男，1：女
     */
    private int sex;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 状态，0：正常，1：锁定，2：删除
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
