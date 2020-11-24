package com.wzy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wzy.util.CustomDoubleSerialize;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/12/19 9:25
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartWithProduct extends Product implements Serializable {

    private static final long serialVersionUID = -108907189034815108L;

    /**
     * 购物车id
     */
    private int id;

    /**
     * 商品id
     */
    private int productId;

    /**
     * 商品属性
     */
    private String productAttr;

    /**
     * 加入购物车的创建时间
     */
    private int cartNumber;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 购物车状态
     */
    private String cartStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cartCreateTime;

}
