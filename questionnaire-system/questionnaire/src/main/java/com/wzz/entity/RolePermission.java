package com.wzz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Clarence1
 */
@Data
@ApiModel
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private int roleId;

    private int permId;

}
