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

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2020/1/12 16:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Logistics implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流表id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 物流编号
     */
    private String logisticsId;

    /**
     * 物流类型
     */
    private String logisticsType;

    /**
     * 子订单编号
     */
    private String childOrderId;

    /**
     * 物流状态
     */
    private String logisticsStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logisticsCreateTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logisticsModifyTime;

    /**
     * 供应者id
     */
    private int supplierId;

    /**
     * 供应者用户名
     */
    private String supplierName;

}
