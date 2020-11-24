package com.wzy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Clarence1
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private int userId;

    private int roleId;

    private String roleName;

    private String userName;

}
