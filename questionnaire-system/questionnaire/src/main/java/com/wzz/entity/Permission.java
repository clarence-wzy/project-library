package com.wzz.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Clarence1
 */
@Data
@ApiModel
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    private int permId;

    private String permName;

    private String permDetail;

}
