package com.ripen.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:49
 * @since 2021/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Operation implements Serializable {

    /**
     * 操作id
     */
    private String opId;

    /**
     * 操作类型
     */
    private int opType;

    /**
     * 操作详情
     */
    private String opDetail;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
