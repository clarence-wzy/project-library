package com.wzy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wzy.util.CustomDoubleSerialize;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2020/1/7 15:01
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 子订单编号
     */
    private String childOrderId;

    /**
     * 主订单编号
     */
    private String masterOrderId;

    /**
     * 商品id
     */
    private int productId;

    /**
     * 商品属性
     */
    private String productAttr;

    /**
     * 商品数量
     */
    private int number;

    /**
     * 物流id
     */
    private String logisticsId;

    /**
     * 子订单订单总价
     */
    @JsonSerialize(using = CustomDoubleSerialize.class)
    private double price;

    /**
     * 子订单状态
     */
    private String childOrderStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliverTime;

    /**
     * 确定收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime evaluateTime;

    /**
     * 卖家id
     */
    private String ownName;

    /**
     * 买家备注
     */
    private String remark;

    /**
     * 商品信息
     */
    private Product product;

    /**
     * 物流信息
     */
    private Logistics logistics;

    /**
     * 主订单信息
     */
    private MasterOrder masterOrder;

}
