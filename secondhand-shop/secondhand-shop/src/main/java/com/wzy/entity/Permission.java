package com.wzy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/11/27 9:25
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    private int permId;

    private String permName;

    private String permDetail;

}
