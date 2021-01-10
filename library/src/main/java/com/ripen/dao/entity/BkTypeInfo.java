package com.ripen.dao.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书籍类型与信息关联实体类
 *
 * @author Ripen.Y
 * @version 2021/01/10 20:52
 * @since 2021/01/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BkTypeInfo implements Serializable {

    /**
     * 类型id
     */
    private int btId;

    /**
     * 书籍id
     */
    private String bkId;

}
