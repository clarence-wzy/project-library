package com.wzy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @Date: 2020/1/12 16:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OrderSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 拥有者
     */
    private String ownName;

    /**
     * 物流编号
     */
    private String logisticsId;

    /**
     * 物流类型
     */
    private String logisticsType;

    /**
     * 订单状态
     */
    private String childOrderStatus;

    /**
     * 子订单编号
     */
    private String childOrderId;

    /**
     * 子订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderStartTime;

    /**
     * 子订单结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderEndTime;

    /**
     * 购买者姓名
     */
    private String consigneeName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品名称对应的商品id
     */
    private int productId;

    /**
     * 物流信息对应的物流id
     */
    private List<String> logisticsIds;

    /**
     * 购买者对应的主订单编号
     */
    private List<String> masterOrderIds;

}
