package com.wzz.entity;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: User</p>
 * <p>Description: 用户实体</p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/26 15:50
 */
@Data
@ApiModel
public class User implements Serializable {

    private long userId;

    private String username;

    private String password;

    private int roleId;

    private String emailCode;

    private int maxEmail;

    private int maxMessage;

    private int maxStorage;

    private int maxAccount;

    private Date latestTime;

}
