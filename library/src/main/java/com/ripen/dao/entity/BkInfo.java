package com.ripen.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 书籍信息实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:30
 * @since 2021/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BkInfo implements Serializable {

    /**
     * 书籍id
     */
    private String bkId;

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
     * 状态，0：正常，1：删除
     */
    private int bkInfoStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    /**
     * 书籍类型列表
     */
    private List<BkType> bkTypeList;

}
