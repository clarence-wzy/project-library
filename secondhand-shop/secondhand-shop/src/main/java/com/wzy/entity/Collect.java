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
 * @Date: 2019/11/27 9:25
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 收藏商品id
     */
    private int productId;

    /**
     * 收藏用户id
     */
    private int userId;

    /**
     * 收藏状态
     */
    private String collectStatus;

    /**
     * 收藏创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectCreateTime;

    /**
     * 收藏更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectModifyTime;


}
