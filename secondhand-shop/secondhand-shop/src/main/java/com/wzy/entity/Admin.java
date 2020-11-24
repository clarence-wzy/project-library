package com.wzy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2020/2/12 22:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    private int adminId;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;

    /**
     * 管理员状态
     */
    private String adminStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adminCreateTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adminModifyTime;

}
