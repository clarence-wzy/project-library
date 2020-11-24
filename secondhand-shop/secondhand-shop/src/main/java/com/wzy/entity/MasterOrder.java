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
import java.util.List;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2020/1/7 15:01
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 主订单编号
     */
    private String masterOrderId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 收货地址id
     */
    private int addressId;

    /**
     * 物流id
     */
    private String logisticsId;

    /**
     * 主订单总价
     */
    @JsonSerialize(using = CustomDoubleSerialize.class)
    private double price;

    /**
     * 主订单状态
     */
    private String masterOrderStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 用户信息
     */
    private User user;

    /**
     * 对应的子订单
     */
    private List<ChildOrder> childOrderList;

    /**
     * 收货地址信息
     */
    private Address address;

}
