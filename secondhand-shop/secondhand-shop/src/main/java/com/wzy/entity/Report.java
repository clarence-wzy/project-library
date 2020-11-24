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
 * @Date: 2020/1/19 17:48
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 举报id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 举报内容
     */
    private String reportContent;

    /**
     * 举报人id
     */
    private int userId;

    /**
     * 举报的商品id
     */
    private int productId;

    /**
     * 举报创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportCreateTime;

    /**
     * 举报状态
     */
    private String reportStatus;

    /**
     * 举报的审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportAuditTime;

    /**
     * 审核结果内容
     */
    private String auditContent;

    /**
     * 扣除信用度
     */
    private int deductCredit;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品拥有者id
     */
    private int ownId;

    /**
     * 商品拥有者
     */
    private String ownName;

}
