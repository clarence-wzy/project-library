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
public class ProductType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品类型id
     */
    private int typeId;

    /**
     * 商品类型名称
     */
    private String typeName;

    /**
     * 商品类型状态
     */
    private String typeStatus;
}
