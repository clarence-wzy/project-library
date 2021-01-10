package com.ripen.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书籍记录实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:30
 * @since 2021/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BkRecord implements Serializable {

    /**
     * 书籍编号
     */
    private String serId;

    /**
     * 书籍id
     */
    private String bkId;

    /**
     * 借出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lendTime;

    /**
     * 归还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime returnTime;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

}
