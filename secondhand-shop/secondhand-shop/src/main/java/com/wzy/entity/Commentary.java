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
 * @Date: 2020/1/18 14:33
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commentary implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评价id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 子订单编号
     */
    private String childOrderId;

    /**
     * 评价人id
     */
    private int userId;

    /**
     * 评价人姓名
     */
    private String username;

    /**
     * 好评1分，中评0分，差评-1分
     */
    private int rate;

    /**
     * 评价内容
     */
    private String commentaryContent;

    /**
     * 评价图片
     */
    private String commentaryImage;

    /**
     * 评价是否匿名
     */
    private String anonymity;

    /**
     * 评价状态
     */
    private String commentaryStatus;

    /**
     * 评价创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentaryCreateTime;

    /**
     * 子订单信息
     */
    private ChildOrder childOrder;

    /**
     * 用户信息
     */
    private User user;

}
