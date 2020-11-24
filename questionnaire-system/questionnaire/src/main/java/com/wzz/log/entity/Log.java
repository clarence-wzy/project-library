package com.wzz.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: Log</p>
 * <p>Description:日志类实体 </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/27 17:25
 */
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    private String logId;

    /**
     * 日志类型
     */
    private String type;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 请求地址
     */
    private String remoteAddr;

    /**
     * URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 提交参数
     */
    private String params;

    /**
     * 异常
     */
    private String exception;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operateDate;

    /**
     * 结束时间
     */
    private String timeout;

}
