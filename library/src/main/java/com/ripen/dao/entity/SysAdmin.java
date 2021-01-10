package com.ripen.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统管理员实体类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:26
 * @since 2021/01/09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SysAdmin implements Serializable {

    /**
     * 管理员id
     */
    private String admId;

    private String pwd;

    /**
     * 性别，0：男，1：女
     */
    private int sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态，0：正常，1：锁定，2：删除
     */
    private int admStatus;

    /**
     * 等级（暂无等级，默认都为0）
     */
    private int level;

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

}
