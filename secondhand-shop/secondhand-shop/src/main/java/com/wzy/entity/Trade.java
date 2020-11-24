package com.wzy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/12/26 23:12
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交易id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tradeId;

    /**
     * 交易名称
     */
    private String tradeName;

}
