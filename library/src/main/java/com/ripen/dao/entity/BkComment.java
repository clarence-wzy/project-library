package com.ripen.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书籍评论实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:44
 * @since 2021/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BkComment implements Serializable {

    /**
     * 评论自增id
     */
    private int id;

    /**
     * 记录id
     */
    private String rcdId;

    /**
     * 书籍id
     */
    private String bkId;

    /**
     * 评论等级，-1：差评，0：中评，1：好评
     */
    private int rate;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String img;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 是否匿名，0：否，1：是
     */
    private int isAnno;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 状态，0：正常，1：删除
     */
    private Integer commStatus;

}
