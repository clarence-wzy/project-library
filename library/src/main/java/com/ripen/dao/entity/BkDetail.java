package com.ripen.dao.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 书籍详情实体类
 *
 * @author Ripen.Y
 * @version 2021/01/10 20:52
 * @since 2021/01/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BkDetail implements Serializable {

    /**
     * 类型名称
     */
    private String serId;

    /**
     * 书籍id
     */
    private String bkId;

    /**
     * 状态，0：正常，1：借出
     */
    private int bkDetailStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 管理员id
     */
    private String admId;


    /**
     * 书籍名称
     */
    private String bkName;

    /**
     * 书籍作者
     */
    private String bkAuthor;

    /**
     * 书籍出版社
     */
    private String bkPress;

    /**
     * 书籍报价
     */
    private BigDecimal bkPrice;

    /**
     * 备注信息
     */
    private String remark;


    /**
     * 书籍类型
     */
    private BkType bkType;

}
