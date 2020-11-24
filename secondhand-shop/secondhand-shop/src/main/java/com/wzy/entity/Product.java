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
import java.util.List;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/11/27 9:25
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = -108907189034815108L;

    /**
     * 商品id
     */
    private int productId;

    /**
     *  商品名称
     */
    private String productName;

    /**
     * 用户id
     */
    private int ownId;

    /**
     * 用户名
     */
    private String ownName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 商品类型id
     */
    private int typeId;

    /**
     * 商品类型名
     */
    private String typeName;

    /**
     * 商品数量
     */
    private int number;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 商品详情图片
     */
    private String detailImage;

    /**
     * 商品价格
     */
    @JsonSerialize(using = CustomDoubleSerialize.class)
    private double price;

    /**
     * 商品成色
     */
    private String qualityName;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationTime;

    /**
     * 交易类型
     */
    private String tradeName;

    /**
     * 交易描述
     */
    private String tradeDecription;

    /**
     * 商品描述
     */
    private String productDecription;

    /**
     * 商品创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime productCreateTime;

    /**
     * 商品修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime productModifyTime;

    /**
     * 商品状态
     */
    private String productStatus;

    /**
     * 商品表备注
     */
    private String remark;

    /**
     * 商品收藏信息
     */
    private List<Collect> collect;

    /**
     * 所有者信息
     */
    private User user;

}
