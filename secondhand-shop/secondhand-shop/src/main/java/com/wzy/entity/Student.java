package com.wzy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/12/29 22:57
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 学号
     */
    private int id;

    /**
     * 密码
     */
    private String password;

}
