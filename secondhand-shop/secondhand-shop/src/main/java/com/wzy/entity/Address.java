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
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号
     */
    private String consigneePhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮编地址
     */
    private int postCode;

    /**
     * 是否为默认地址
     */
    private String defaultAddress;

    /**
     * 地址状态
     */
    private String addressStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    /**
     * 所属用户id
     */
    private int ownId;

    /**
     * 所属用户姓名
     */
    private String ownName;

}
