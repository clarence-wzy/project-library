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
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private int roleId;

    private int permId;

    private String roleName;

    private String permName;

}
