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
 * @Date: 2020/2/12 16:02
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 管理员id
     */
    private int adminId;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 操作内容
     */
    private String logContent;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * 日志创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logTime;

}
