package com.ripen.dao.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书籍类型实体类
 *
 * @author Ripen.Y
 * @version 2021/01/10 20:52
 * @since 2021/01/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BkType implements Serializable {

    /**
     * 类型id
     */
    private int btId;

    /**
     * 类型名称
     */
    private String tName;

    /**
     * 状态，0：正常，1：删除
     */
    private Integer btStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    /**
     * 书籍信息数量
     */
    int infoNum;

}
