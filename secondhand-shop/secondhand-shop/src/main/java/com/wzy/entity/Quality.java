package com.wzy.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * @Package: com.wzy.entity
 * @Author: Clarence1
 * @Date: 2019/12/26 23:12
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quality implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成色id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qualityId;

    /**
     * 成色名称
     */
    private String qualityName;

}
